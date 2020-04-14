package com.prankur.eCommerce.events;

import com.prankur.eCommerce.models.User;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnRegistrationCompleteEvent extends ApplicationEvent
{
    User user;
    String token;
    String appUrl;
    Locale locale;

    public OnRegistrationCompleteEvent(User user, String token, String appUrl, Locale locale) {
        super(user);
        this.user = user;
        this.token = token;
        this.appUrl = appUrl;
        this.locale = locale;
    }

    public OnRegistrationCompleteEvent(User user, String token) {
        super(user);
        this.user = user;
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
