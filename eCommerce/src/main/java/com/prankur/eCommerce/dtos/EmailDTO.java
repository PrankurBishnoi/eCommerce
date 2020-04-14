package com.prankur.eCommerce.dtos;

import javax.validation.constraints.Email;

public class EmailDTO
{
    @Email
    private String email;

    public EmailDTO(@Email String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmailDTO() {
    }
}
