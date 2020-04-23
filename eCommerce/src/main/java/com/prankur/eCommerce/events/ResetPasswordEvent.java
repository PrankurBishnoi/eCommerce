package com.prankur.eCommerce.events;

import com.prankur.eCommerce.models.users.User;
import org.springframework.context.ApplicationEvent;

public class ResetPasswordEvent  extends ApplicationEvent
{
    User user;

    public ResetPasswordEvent(User user) {
        super(user);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
