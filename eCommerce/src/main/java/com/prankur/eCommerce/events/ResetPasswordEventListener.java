package com.prankur.eCommerce.events;

import com.prankur.eCommerce.models.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

public class ResetPasswordEventListener implements ApplicationListener<ResetPasswordEvent>
{
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.from.email}")
    private String from;

    @Override
    public void onApplicationEvent(ResetPasswordEvent event)
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

    private void sendingMail(ResetPasswordEvent event)
    {
        System.out.println(3);
        User user =event.user;
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(this.from);
        email.setTo(user.getEmail());
        email.setSubject("Reset Password");
        email.setText("Password has been reset");
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
