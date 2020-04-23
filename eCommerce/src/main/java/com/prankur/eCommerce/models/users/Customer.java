package com.prankur.eCommerce.models.users;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.prankur.eCommerce.models.Address;
import com.prankur.eCommerce.models.Roles;
import com.prankur.eCommerce.models.product.ProductReview;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@DiscriminatorColumn()
@Entity
@JsonFilter("CustomerFilter")
@PrimaryKeyJoinColumn(name = "id")
public class Customer extends User {

    private long contact;

//
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<ProductReview> productReviews;

    public Customer(String email, String firstName, String middleName, String lastName, String password,
                    Boolean isDeleted, Boolean isActive, Set<Address> address, List<Roles> authorities, boolean isExpired, boolean isLocked, boolean isCredentialsExpired, boolean isEnabled, Integer falseAttemptCount,
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

//    public Set<Address> getAddresses()
//    {
//        return super.getAddresses();
//    }


//    public Set<ProductReview> getProductReviews() {
//        return productReviews;
//    }
//
//    public void setProductReviews(Set<ProductReview> productReviews) {
//        this.productReviews = productReviews;
//    }

    @Override
    public String toString() {
        return "Customer{" +
                "contact=" + contact +
                '}';
    }

    public Customer() {
    }
}
