package com.prankur.eCommerce.events;

import com.prankur.eCommerce.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class OnCustomerRegistrationEmailEventListener implements ApplicationListener<OnCustomerRegistrationEmailEvent>
{
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.from.email}")
    private String fromEmail;

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    @Override
    public void onApplicationEvent(OnCustomerRegistrationEmailEvent event)
    {
        this.sendingEmail(event);
    }

    @Async
    private void sendMail(SimpleMailMessage simpleMailMessage)
    {
        System.out.printf(simpleMailMessage.toString());
        mailSender.send(simpleMailMessage);
    }

    private void sendingEmail(OnCustomerRegistrationEmailEvent event)
    {
        User user = event.getUser();
        String token = event.getToken();
        String confirmationUrl = "/customer/registrationConfirmation?token=" + token;
        String recipientAddress = user.getEmail();
        String subject = "Account Activation Key";
        SimpleMailMessage email =new SimpleMailMessage();
        email.setFrom(this.fromEmail);
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText("Click on the Link to Register: "+"http://localhost:8080"+confirmationUrl);
        user.setVerificationMail(email.getText());
        System.out.println(email.getText());
        sendMail(email);
    }






}
