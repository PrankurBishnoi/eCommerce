package com.prankur.eCommerce.cos;

import javax.validation.constraints.Email;

public class EmailCO
{
    @Email
    private String email;

    public EmailCO(@Email String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmailCO() {
    }
}
