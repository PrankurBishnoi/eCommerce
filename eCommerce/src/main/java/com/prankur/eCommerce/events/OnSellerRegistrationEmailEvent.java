package com.prankur.eCommerce.events;

import com.prankur.eCommerce.models.User;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnSellerRegistrationEmailEvent extends ApplicationEvent
{
    User user;
    Locale locale;

    public OnSellerRegistrationEmailEvent(User user, Locale locale) {
        super(user);
        this.user = user;
        this.locale = locale;
    }

    public OnSellerRegistrationEmailEvent(User user) {
        super(user);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

}
