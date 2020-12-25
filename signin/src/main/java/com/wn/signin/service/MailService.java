package com.wn.signin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @program: signin
 * @description: 邮件发送服务
 * @author: Jason-Wei
 * @create: 2020-12-24 15:50
 **/
@Service
@Slf4j
public class MailService {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    public void sendMsg(String to, String title, String content){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(title);
        message.setText(content);
        mailSender.send(message);
        log.info("消息已发送");
    }

    public void sendVertifyCode(String to){
        SimpleMailMessage message = new SimpleMailMessage();
        String rc = getRandomCode(6);
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("您好，"+to + ":\n");
        message.setText("您最近申请了邮箱验证，您的验证码是： "+rc+ "\n 此验证码30分钟内有效。");
        mailSender.send(message);
        log.warn("验证码: {} 已发送",rc);
    }


    public static String getRandomCode(Integer code){
        Random random = new Random();
        StringBuffer result= new StringBuffer();
        for (int i=0;i<code;i++){
            result.append(random.nextInt(10));
        }
        return result.toString();
    }

}
