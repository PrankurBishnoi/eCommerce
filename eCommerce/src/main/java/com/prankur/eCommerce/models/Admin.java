package com.prankur.eCommerce.models;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.List;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Admin extends User
{
    public Admin(String email, String firstName, String middleName, String lastName, String password, Set<Address> addresses, List<GrantAuthorityImpl> authorities) {
        super(email, firstName, middleName, lastName, password, addresses, authorities);
    }

    @Override
    public String toString() {
        return "Admin{}";
    }

    public Admin() {
    }
}
