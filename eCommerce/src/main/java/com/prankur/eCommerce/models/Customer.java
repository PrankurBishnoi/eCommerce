package com.prankur.eCommerce.models;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Customer extends User {

    private long contact;

    public Customer(String email, String firstName, String middleName, String lastName, String password,
                    Boolean isDeleted, Boolean isActive, Set<Address> address, List<GrantAuthorityImpl> authorities, boolean isAccountNotExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired, boolean isEnabled, Integer falseAttemptCount,
                    long contact)
    {
        super(email, firstName, middleName, lastName, password, isDeleted, isActive, address, authorities, isAccountNotExpired, isAccountNonLocked, isCredentialsNonExpired, isEnabled, falseAttemptCount);
        this.contact = contact;
    }

    public long getContact() {
        return contact;
    }

    public void setContact(long contact) {
        this.contact = contact;
    }
}
