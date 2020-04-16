package com.prankur.eCommerce.models;

import com.fasterxml.jackson.annotation.JsonFilter;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

@Entity
@JsonFilter("CustomerFilter")
@PrimaryKeyJoinColumn(name = "user_id")
public class Customer extends User {

    private long contact;

    public Customer(String email, String firstName, String middleName, String lastName, String password,
                    Boolean isDeleted, Boolean isActive, Set<Address> address, List<GrantAuthorityImpl> authorities, boolean isExpired, boolean isLocked, boolean isCredentialsExpired, boolean isEnabled, Integer falseAttemptCount,
                    long contact)
    {
        super(email, firstName, middleName, lastName, password, isDeleted, isActive, address, authorities, isExpired, isLocked, isCredentialsExpired, isEnabled, falseAttemptCount);
        this.contact = contact;
    }

    public long getContact() {
        return contact;
    }

    public void setContact(long contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "contact=" + contact +
                '}';
    }

    public Customer() {
    }
}
