package com.prankur.eCommerce.events;

import com.prankur.eCommerce.utils.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

public class OnAccountLLockedEventListener implements ApplicationListener<OnAccountLLockedEvent>
{
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.from.email}")
    private String from;

    @Override
    public void onApplicationEvent(OnAccountLLockedEvent event)
    {
        System.out.println(2);
        this.sendingMail(event);
    }

    @Async
    private  void sendMail(SimpleMailMessage simpleMailMessage)
    {
        System.out.println(simpleMailMessage.toString());
        mailSender.send(simpleMailMessage);
    }

    private void sendingMail(OnAccountLLockedEvent event)
    {
        System.out.println(3);
        Email email1 =event.getEmail();
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(this.from);
        email.setTo(email1.getTo());
        email.setSubject(email1.getSubject());
        email.setText(email1.getMessage());
        System.out.println(4);
        sendMail(email);
    }


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}