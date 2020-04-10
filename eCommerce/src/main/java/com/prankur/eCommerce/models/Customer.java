package com.prankur.eCommerce.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Customer extends User {

    private String contact;

    public Customer(String email, String firstName, String middleName, String lastName, String password, Boolean isDeleted, Boolean isActive, Set<Address> address, List<GrantAuthorityImpl> authorities, boolean isAccountNotExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired, boolean isEnabled, Integer falseAttemptCount, String contact) {
        super(email, firstName, middleName, lastName, password, isDeleted, isActive, address, authorities, isAccountNotExpired, isAccountNonLocked, isCredentialsNonExpired, isEnabled, falseAttemptCount);
        this.contact = contact;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
