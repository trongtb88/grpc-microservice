package com.dragon88.seatservice.controller; /**
 * 
 */

import com.dragon88.gen.proto.*;
import com.dragon88.seatservice.dao.SeatEntity;
import com.dragon88.seatservice.service.SeatService;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author thanh
 *
 */
@GRpcService
@RequestMapping("app")
public class SeatController extends SeatControllerGrpc.SeatControllerImplBase {

	private static final Logger LOGGER = LoggerFactory.getLogger(SeatController.class);

	@Autowired
	private SeatService seatService;

	@Override
	public void reserve(SeatList request, StreamObserver<ReserveSeatResponse> responseObserver) {
		LOGGER.info("reserve received {}", request);

		if (!seatService.validateSeatListBoundaries(request.getSeatsList())) {
			ReserveSeatResponse reserveSeatReply = ReserveSeatResponse.newBuilder().setSuccess(false)
					.setMessage("can't reserve seats").build();
			LOGGER.info("server responded failed validation {}", reserveSeatReply);
			responseObserver.onNext(reserveSeatReply);
			responseObserver.onCompleted();
			return;
		}
		for (Seat seat: request.getSeatsList()) {
			LOGGER.info("trying to reserve seat {}", seat);

		}

		if (seatService.reserve(request.getSeatsList())) {
			ReserveSeatResponse reserveSeatReply = ReserveSeatResponse.newBuilder().setSuccess(true)
					.setMessage("reserve seats successfuly").build();
			LOGGER.info("server responded {}", reserveSeatReply);
			responseObserver.onNext(reserveSeatReply);
			responseObserver.onCompleted();
			return;
		}
		
		ReserveSeatResponse reserveSeatReply = ReserveSeatResponse.newBuilder().setSuccess(false)
				.setMessage("reserve seats failed").build();
		LOGGER.info("server responded {}", reserveSeatReply);
		responseObserver.onNext(reserveSeatReply);
		responseObserver.onCompleted();
	}

	public void get(NumberOfSeats request, StreamObserver<SeatList> responseObserver) {
		LOGGER.info("get received {}", request);

		if (!seatService.validateSeatNumber(request.getTotal())) {
			SeatList reserveSeatReply = SeatList.newBuilder().build();
			LOGGER.info("server responded failed validation {}", reserveSeatReply);
			responseObserver.onNext(reserveSeatReply);
			responseObserver.onCompleted();
		}

		List<SeatEntity> seats = seatService.getSeats(request.getTotal());

		SeatList.Builder seatList = SeatList.newBuilder();
		for (SeatEntity seat : seats) {
			Seat value = Seat.newBuilder().setRow(seat.getSeatIdentity().getIndexRow()).setColumn(seat.getSeatIdentity().getIndexColumn()).setStatus(seat.getStatus()).build();
			seatList.addSeats(value);
		}
		responseObserver.onNext(seatList.build());
		responseObserver.onCompleted();
	}

}
