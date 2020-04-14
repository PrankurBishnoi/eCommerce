package com.prankur.eCommerce.models;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.List;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Seller extends User
{
    private String gst;
    private long companyContact;
    private String companyName;

    public Seller(String email, String firstName, String middleName, String lastName, String password, Boolean isDeleted, Boolean isActive, Set<Address> addresses, List<GrantAuthorityImpl> authorities, boolean isAccountNotExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired, boolean isEnabled, Integer falseAttemptCount, String gst, long companyContact, String companyName) {
        super(email, firstName, middleName, lastName, password, isDeleted, isActive, addresses, authorities, isAccountNotExpired, isAccountNonLocked, isCredentialsNonExpired, isEnabled, falseAttemptCount);
        this.gst = gst;
        this.companyContact = companyContact;
        this.companyName = companyName;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public long getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(long companyContact) {
        this.companyContact = companyContact;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "gst='" + gst + '\'' +
                ", companyContact='" + companyContact + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
