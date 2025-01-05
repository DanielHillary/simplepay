package com.simbrella.simplepay.user_management.service;//package com.simbrella.simplepay.user_management.service;
//
//
//import com.simbrella.simplepay.user_management.model.User;
//import lombok.RequiredArgsConstructor;
//import org.jobrunr.jobs.annotations.Job;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMailMessage;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//import org.thymeleaf.context.Context;
//import org.thymeleaf.spring6.SpringTemplateEngine;
//
//import java.util.Map;
//
//
///**
// * This class is used to send email with and without attachment.
// * @author w3spoint
// */
//
//@Service
//@RequiredArgsConstructor
//public class EmailService {
//
//    @Value("${spring.mail.username}")
//    private String EMAIL_SENDER;
//
//    private final JavaMailSender javaMailSender;
//
//    private final SpringTemplateEngine thymeleafTemplateEngine;
//
//    public void sendSimpleMessage(String to, String subject, String body){
//
//        SimpleMailMessage message = new SimpleMailMessage();
//
//        message.setTo(to);
//        message.setFrom(EMAIL_SENDER);
//        message.setSubject(subject);
//        message.setText(body);
//
//        javaMailSender.send(message);
//    }
//
//
//    public void sendRegistrationNotification(User user) {
//
//        try {
//            Context context = new Context();
//            context.setVariable("user", user);
//            String message = "Your Verification OTP is : " + user.getVerificationOtp();
//            sendSimpleMessage(user.getEmail(), "PsyDTrader Registration", message);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public void sendNotificationWelcome(User user) {
//
//    }
//}