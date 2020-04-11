package com.prankur.eCommerce.dtos;

import com.prankur.eCommerce.models.Address;
import com.prankur.eCommerce.validators.MatchingPassword;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.util.Set;

@MatchingPassword
public class SellerRegistrationDTO  extends PasswordDTO
{
    @NotBlank(message = "Email cannot be null")
    @Email(message = "Email constraint violation")
    private String email;

    @NotBlank(message = "FirstName cannot be Null")
    private String firstName;

    @NotNull
    private String middleName;

    @NotBlank(message = "Last Name cannot be null")
    private String lastName;

    private Set<Address> address;

//    @Pattern(regexp="^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$",message = "Invalid GST")
    private String gst;

    @NotBlank
    @Column(length = 10)
    private String companyContact;

    @NotBlank(message = "Company Name cannot be Null")
    private String companyName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Address> getAddress() {
        return address;
    }

    public void setAddress(Set<Address> address) {
        this.address = address;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
