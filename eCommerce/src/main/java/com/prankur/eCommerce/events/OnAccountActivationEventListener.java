package com.prankur.eCommerce.events;

import com.prankur.eCommerce.utils.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class OnAccountActivationEventListener implements ApplicationListener<OnAccountActivationEvent>
{

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public void onApplicationEvent(OnAccountActivationEvent event)
    {
        sendingMail(event);
    }

    @Async
    private void sendMail(SimpleMailMessage email)
    {
        javaMailSender.send(email);
    }

    private void sendingMail(OnAccountActivationEvent event)
    {
        Email email  = event.getEmail();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email.getTo());
        simpleMailMessage.setFrom(email.getFrom());
        simpleMailMessage.setSubject(email.getSubject());
        simpleMailMessage.setText(email.getMessage());
        sendMail(simpleMailMessage);
    }


}
