package com.prankur.eCommerce.cos;

import com.prankur.eCommerce.validators.MatchingPassword;
import com.prankur.eCommerce.validators.ValidPassword;

@MatchingPassword
public class PasswordCO
{
    @ValidPassword(message = "length 8-15,atleast 1 upper,1 lower, 1 digit, 1 special")
    private String password;
    private String confirmPassword;

    public PasswordCO(String password, String confirmPassword) {
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public PasswordCO() {
    }
}
