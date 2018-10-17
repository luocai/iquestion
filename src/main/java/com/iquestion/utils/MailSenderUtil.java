package com.iquestion.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailSenderUtil {

    @Autowired
    JavaMailSender jms;

    public void sendMail(String subject,String toAccount,String text){

        //建立邮件消息
        SimpleMailMessage mainMessage = new SimpleMailMessage();
        //发送者
        mainMessage.setFrom("15958038249@163.com");
        //接收者
        mainMessage.setTo(toAccount);
        //发送的标题
        mainMessage.setSubject(subject);

        //发送的内容
        mainMessage.setText(text);
        jms.send(mainMessage);

    }

}
