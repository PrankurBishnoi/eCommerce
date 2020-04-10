package com.prankur.eCommerce.enums;

public enum Roles
{
    CUSTOMER("ROLE_CUSTOMER"),SELLER("ROLE_SELLER"),ADMIN("ROLE_ADMIN");

    private String roles;

    Roles(String roles) {
        this.roles = roles;
    }

    public String getRoles() { return roles; }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
