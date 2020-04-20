package com.prankur.eCommerce.events;

import com.prankur.eCommerce.models.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

public class ForgotPasswordCompleteEventListener  implements ApplicationListener<ForgotPasswordCompleteEvent>
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
    public void onApplicationEvent(ForgotPasswordCompleteEvent event)
    {
        this.sendingEmail(event);
    }

    @Async
    private void sendMail(SimpleMailMessage simpleMailMessage)
    {
        System.out.printf(simpleMailMessage.toString());
        mailSender.send(simpleMailMessage);
    }

    private void sendingEmail(ForgotPasswordCompleteEvent event)
    {
        User user = event.getUser();
        String token = event.getToken();
        String confirmationUrl = "/user/resetPassword?token=" + token;
        String recipientAddress = user.getEmail();
        String subject = "Link to Reset Password";
        SimpleMailMessage email =new SimpleMailMessage();
        email.setFrom(this.fromEmail);
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText("Click on the Link to Generate new Password: "+"http://localhost:8080"+confirmationUrl);
        user.setVerificationMail(email.getText());
        System.out.println(email.getText());
        sendMail(email);
    }
}
