package com.ctgu.swzl.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class Jmail {

    @Value("${mail.account}")
    private String mailName;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.from}")
    private String fromMail;

    private String toMail;

    private String content;

    private String topic;

    public void setToMail(String toMail) {
        this.toMail = toMail;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // 邮件发送
    public void send() throws MessagingException {

        Properties properties = new Properties();
        properties.setProperty("mail.host","smtp.qq.com");
        properties.setProperty("mail.smtp.auth","true");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailName,password);
            }
        });
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromMail));
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(toMail));
        message.setSubject(topic);
        message.setContent(content,"text/html;charset=utf-8;");
        Transport.send(message);
    }

    @Override
    public String toString() {
        return "Jmail{" +
                "mailName='" + mailName + '\'' +
                ", password='" + password + '\'' +
                ", fromMail='" + fromMail + '\'' +
                ", toMail='" + toMail + '\'' +
                ", content='" + content + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }
}
