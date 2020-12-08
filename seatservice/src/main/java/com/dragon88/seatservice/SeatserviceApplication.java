package com.dragon88.seatservice;


import com.dragon88.seatservice.dao.SeatEntity;
import com.dragon88.seatservice.dao.SeatIdentity;
import com.dragon88.seatservice.repository.SeatEntityRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EntityScan("com.dragon88.seatservice.dao")
public class SeatserviceApplication {

    @Autowired
    private AppProperties myAppProperties;

    @Autowired
    private SeatEntityRepository seatEntityRepository;

    public static void main(String[] args) {
        SpringApplication.run(SeatserviceApplication.class, args);
    }

    @Bean
    InitializingBean populateAvailableSeatsInDatabase() {
        return () -> {
            for (int row = 0; row < myAppProperties.getCinemaRow(); row++) {
                for (int column = 0; column < myAppProperties.getCinemaColumn(); column++) {
                    SeatIdentity seatIdentity = new SeatIdentity();
                    seatIdentity.setIndexRow(row);
                    seatIdentity.setIndexColumn(column);
                    SeatEntity seatEntity = SeatEntity.builder()
                            .seatIdentity(seatIdentity)
                            .status("empty").build();
                    if (!seatEntityRepository.findById(seatIdentity).isPresent()) {
                        seatEntityRepository.save(seatEntity);
                    }
                }
            }
        };
    }
}
