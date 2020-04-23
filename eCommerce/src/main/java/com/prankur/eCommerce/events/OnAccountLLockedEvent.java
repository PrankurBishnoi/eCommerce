package com.prankur.eCommerce.events;

import com.prankur.eCommerce.utils.Email;
import org.springframework.context.ApplicationEvent;

public class OnAccountLLockedEvent extends ApplicationEvent
{
    private Email email;


    public OnAccountLLockedEvent(Email email) {
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
