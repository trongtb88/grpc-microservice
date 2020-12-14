package com.dragon88.userservice.service;

import com.dragon88.userservice.dto.UserReserve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class ProducerService {
    @Autowired
    private KafkaTemplate<String, UserReserve> userReserveKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value(value = "${kafka.topic.name}")
    private String topicName;

    public void sendMessage(String message) {

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata()
                        .offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
            }
        });
    }

    public void sendUserReserveMessage(UserReserve userReserve) {
        ListenableFuture<SendResult<String, UserReserve>> future = userReserveKafkaTemplate.send(topicName, userReserve);
        future.addCallback(new ListenableFutureCallback<SendResult<String, UserReserve>>() {
            @Override
            public void onSuccess(SendResult<String, UserReserve> result) {
                System.out.println("Sent message=[" + userReserve.toString() + "] with offset=[" + result.getRecordMetadata()
                        .offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=[" + userReserve.toString() + "] due to : " + ex.getMessage());
            }
        });
    }

}

