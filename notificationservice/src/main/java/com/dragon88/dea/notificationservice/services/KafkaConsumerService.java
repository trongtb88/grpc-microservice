package com.dragon88.dea.notificationservice.services;

import com.dragon88.dea.notificationservice.dto.UserReserve;
import com.dragon88.dea.notificationservice.email.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @Autowired
    EmailService emailService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(topics = "${kafka.topic.name}", containerFactory = "userReserveKafkaListenerContainerFactory")
    public void userReserveListener(UserReserve userReserve) {
        System.out.println("Received userReserve message: " + userReserve);
        logger.info("Received userReserve message : {} " ,userReserve);
        if (emailService != null) {
            emailService.sendUserReserve(userReserve);
        }
    }
}
