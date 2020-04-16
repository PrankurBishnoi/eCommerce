package com.prankur.eCommerce.models;

import com.fasterxml.jackson.annotation.JsonFilter;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.List;
import java.util.Set;

@Entity
@JsonFilter("SellerFilter")
@PrimaryKeyJoinColumn(name = "id")
public class Seller extends User
{
    private String gst;
    private long companyContact;
    private String companyName;

    public Seller(String email, String firstName, String middleName, String lastName, String password, Boolean isDeleted, Boolean isActive, Set<Address> addresses, List<GrantAuthorityImpl> authorities, boolean isExpired, boolean isLocked, boolean isCredentialsExpired, boolean isEnabled, Integer falseAttemptCount, String gst, long companyContact, String companyName) {
        super(email, firstName, middleName, lastName, password, isDeleted, isActive, addresses, authorities, isExpired, isLocked, isCredentialsExpired, isEnabled, falseAttemptCount);
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

    public Seller() {
    }
}
