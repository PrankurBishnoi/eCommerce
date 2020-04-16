package com.prankur.eCommerce.events;

import com.prankur.eCommerce.models.User;
import org.springframework.context.ApplicationEvent;

public class ForgotPasswordCompleteEvent extends ApplicationEvent
{
    User user;
    String token;
    String locale;

    public ForgotPasswordCompleteEvent( User user, String token, String locale) {
        super(user);
        this.token = token;
        this.locale = locale;
    }

    public ForgotPasswordCompleteEvent(User user, String token) {
        super(user);
        this.token = token;
    }

    public ForgotPasswordCompleteEvent( User user) {
        super(user);
    }



}
