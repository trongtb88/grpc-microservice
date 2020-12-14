package com.dragon88.userservice.service;

import com.dragon88.gen.proto.*;
import com.dragon88.userservice.dao.User;
import com.dragon88.userservice.repository.UserRepository;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.dragon88.userservice.dto.SeatDTO;
import com.dragon88.userservice.dto.UserReserve;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);


    @Qualifier("eurekaClient")
    @Autowired
    private EurekaClient client;

    @Autowired
    private ProducerService producerService;
    @Autowired
    private UserRepository userRepository;


    public ReserveSeatResponse reserve(UserReserve userReserve) {
        LOGGER.info("reserve: {}", userReserve);
        saveOrUpdateUser(userReserve);
        final InstanceInfo instanceInfo = client.getNextServerFromEureka("seat-service", false);
        final ManagedChannel channel = ManagedChannelBuilder.forAddress(instanceInfo.getIPAddr(), instanceInfo.getPort())
                .usePlaintext()
                .build();
        SeatControllerGrpc.SeatControllerBlockingStub stub = SeatControllerGrpc.newBlockingStub(channel);
        List<SeatDTO> seatDTOS = userReserve.getSeats();
        List<Seat> seats = new ArrayList<>();
        for (SeatDTO seatDTO : seatDTOS) {
            seats.add(Seat.newBuilder().setRow(seatDTO.getRows()).setColumn(seatDTO.getCols()).build());
        }
        SeatList addAllSeats = SeatList.newBuilder().addAllSeats(seats).build();
        ReserveSeatResponse reserveSeatResponse = stub.reserve(addAllSeats);
        channel.shutdown();
        sendObjectReserveViaKafka(userReserve, reserveSeatResponse);
        return reserveSeatResponse;
    }

    private void sendObjectReserveViaKafka(UserReserve userReserve, ReserveSeatResponse reserveSeatResponse) {
        if (StatusCode.OK.equals(reserveSeatResponse.getStatusCode())) {
            LOGGER.info("Sending to notification kafka");
            userReserve.setBookedResponse(reserveSeatResponse.getMessage());
            producerService.sendUserReserveMessage(userReserve);
            LOGGER.info("Finished sent to notification kafka");
        }
    }

    private void saveOrUpdateUser(UserReserve userReserve) {
        User user = new User();
        user.setId(userReserve.getId().longValue());
        user.setName(userReserve.getName());
        user.setEmail(userReserve.getEmail());
        userRepository.save(user);
    }
}
