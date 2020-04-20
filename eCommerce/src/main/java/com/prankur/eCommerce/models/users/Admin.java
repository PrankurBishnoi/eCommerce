package com.prankur.eCommerce.models.users;

import com.prankur.eCommerce.models.Address;
import com.prankur.eCommerce.models.GrantAuthorityImpl;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.List;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Admin extends User
{
//    public Admin(String email, String firstName, String middleName, String lastName, String password, Set<Address> addresses, List<GrantAuthorityImpl> authorities) {
//        super(email, firstName, middleName, lastName, password, addresses, authorities);
//    }

    public Admin(String email, String firstName, String middleName, String lastName, String password, Boolean isDeleted, Boolean isActive, Set<Address> addresses, List<GrantAuthorityImpl> grantAuthorities, boolean isExpired, boolean isLocked, boolean isCredentialsExpired, boolean isEnabled, Integer falseAttemptCount) {
        super(email, firstName, middleName, lastName, password, isDeleted, isActive, addresses, grantAuthorities, isExpired, isLocked, isCredentialsExpired, isEnabled, falseAttemptCount);
    }

    @Override
    public String toString() {
        return "Admin{}";
    }

    public Admin() {
    }
}
