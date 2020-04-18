package com.prankur.eCommerce.models;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@JsonFilter("UserFilter")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
    private Boolean isDeleted;
    private Boolean isActive;
    private String verificationMail;
    private boolean isExpired;
    private boolean isLocked;
    private  boolean isCredentialsExpired;
    private  boolean isEnabled;
    private Integer falseAttemptCount;

    private boolean isAccountNotExpired;
    private boolean isAccountNonLocked;
    private  boolean isCredentialsNonExpired;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Address> addresses;


    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<GrantAuthorityImpl> grantAuthorities;

    public User(String email, String firstName, String middleName, String lastName, String password, Boolean isDeleted, Boolean isActive, Set<Address> addresses, List<GrantAuthorityImpl> grantAuthorities, boolean isExpired, boolean isLocked, boolean isCredentialsExpired, boolean isEnabled, Integer falseAttemptCount) {
        this.email = email;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.password = password;
        this.isDeleted = isDeleted;
        this.isActive = isActive;
        this.addresses = addresses;
        this.grantAuthorities = grantAuthorities;
        this.isExpired = isExpired;
        this.isLocked = isLocked;
        this.isCredentialsExpired = isCredentialsExpired;
        this.isEnabled = isEnabled;
        this.falseAttemptCount = falseAttemptCount;
        this.isAccountNonLocked=true;
        this.isAccountNotExpired= true;
        this.isCredentialsNonExpired=true;
    }

    public User(String email, String firstName, String middleName, String lastName, String password, Set<Address> addresses, List<GrantAuthorityImpl> grantAuthorities) {
        this.email = email;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.password = password;
        this.addresses = addresses;
        this.grantAuthorities = grantAuthorities;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public List<GrantAuthorityImpl> getGrantAuthorities() {
        return grantAuthorities;
    }

    public void setGrantAuthorities(List<GrantAuthorityImpl> grantAuthorities) {
        this.grantAuthorities = grantAuthorities;
    }

    public Set<Address> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public Integer getFalseAttemptCount() {
        return falseAttemptCount;
    }

    public void setFalseAttemptCount(Integer falseAttemptCount) {
        this.falseAttemptCount = falseAttemptCount;
    }

    public String getVerificationMail() {
        return verificationMail;
    }

    public void setVerificationMail(String verificationMail) {
        this.verificationMail = verificationMail;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public boolean isCredentialsExpired() {
        return isCredentialsExpired;
    }

    public void setCredentialsExpired(boolean credentialsExpired) {
        isCredentialsExpired = credentialsExpired;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isAccountNotExpired() {
        return isAccountNotExpired;
    }

    public void setAccountNotExpired(boolean accountNotExpired) {
        isAccountNotExpired = accountNotExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    public User() {
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", isDeleted='" + isDeleted + '\'' +
                ", isActive='" + isActive + '\'' +
                ", grantauthorities=" + grantAuthorities +
                ", isAccountNotExpired=" + isExpired +
                ", isAccountNonLocked=" + isLocked +
                ", isCredentialsNonExpired=" + isCredentialsExpired +
                ", isEnabled=" + isEnabled +
                ", falseAttemptCount=" + falseAttemptCount +
                ", address=" + addresses +
                '}';
    }

    public void addAddress(Address address)
    {
        if (address!=null) {
            if (addresses == null)
                addresses = new HashSet<>();
            address.setUser(this);
            addresses.add(address);
        }
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantAuthorities;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return email;
    }

    public boolean isAccountNonExpired() {
        return !isExpired;
    }

    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    public boolean isCredentialsNonExpired() {
        return !isCredentialsExpired;
    }

    public boolean isEnabled() {
        return this.isActive;
    }
}
