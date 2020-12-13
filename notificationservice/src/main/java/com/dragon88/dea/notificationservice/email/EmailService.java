package com.dragon88.dea.notificationservice.email;

import com.dragon88.dea.notificationservice.email.EmailConfig;
import com.dragon88.dea.notificationservice.dto.UserReserve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailService {


    @Autowired
    EmailConfig emailConfig;
    /***
     * Send email to reserve information.
     * @param userReserve
     */

    public void sendUserReserve(UserReserve userReserve) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("trongtb1988@gmail.com");
        message.setTo(userReserve.getEmail());
        message.setSubject("Email from VEF DEA");
        message.setText("User " + userReserve.getName() + " book seats " + userReserve.getSeats() + " result {} " + userReserve.getBookedResponse()) ;
        emailConfig.getJavaMailSender().send(message);
    }
}
