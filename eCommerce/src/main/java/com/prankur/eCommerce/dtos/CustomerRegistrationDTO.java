package com.prankur.eCommerce.dtos;

import com.prankur.eCommerce.models.Address;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;


public class CustomerRegistrationDTO extends PasswordDTO
{
    @NotBlank(message = "Email is Mandatory")
    @Email(message = "Not a Proper Email //nPlease check your Email")
    private String email;

    @NotBlank(message = "First Name is Mandatory")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Last Name is Mandatory")
    private String lastName;

    private Set<Address> addresses;

    @Column(length = 10)
    private long contact;

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

    public long getContact() {
        return contact;
    }

    public void setContact(long contact) {
        this.contact = contact;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public CustomerRegistrationDTO()
    {}

}
