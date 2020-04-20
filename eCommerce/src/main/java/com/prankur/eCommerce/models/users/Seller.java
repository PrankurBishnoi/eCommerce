package com.prankur.eCommerce.models.users;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.prankur.eCommerce.models.Address;
import com.prankur.eCommerce.models.GrantAuthorityImpl;
import com.prankur.eCommerce.models.product.Product;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "seller",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Product> products;

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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
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
