package com.prankur.eCommerce.events;

import com.prankur.eCommerce.utils.Email;
import org.springframework.context.ApplicationEvent;

public class OnAccountActivationEvent extends ApplicationEvent
{
    private Email email;

    public OnAccountActivationEvent( Email email) {
        super(email);
        this.email = email;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }
}
