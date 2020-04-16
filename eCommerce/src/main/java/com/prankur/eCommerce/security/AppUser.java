package com.prankur.eCommerce.security;

import com.prankur.eCommerce.models.Address;
import com.prankur.eCommerce.models.GrantAuthorityImpl;
import com.prankur.eCommerce.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class AppUser implements UserDetails
{
    private Long id;

    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;

    private boolean isDeleted;
    private boolean isActive;
    private boolean isExpired;
    private boolean isLocked;
    private Integer falseAttemptCount;

    private List<GrantAuthorityImpl> grantAuthorities;

    private Set<Address> addresses;

    public AppUser(User user){
//        Set<Role> rolesset = new HashSet<>();
//        Set<Role> temp = user.getRoles();
//        for(Role grantAuthority:temp)
//            rolesset.add(grantAuthority);
        // this.id = user.getId();
        this.email = user.getEmail();
//        this.firstName = user.getFirstName();
//        this.middleName = user.getMiddleName();
//        this.lastName = user.getLastName();
        this.password = user.getPassword();
        System.out.println(user.getPassword());
        this.isActive = user.getIsActive();
        this.isDeleted = user.getIsDeleted();
        this.isExpired = user.isExpired();
        this.isLocked = user.isLocked();

        // this.roles=rolesset;

        this.grantAuthorities = user.getGrantAuthorities();

        // this.addresses = new HashSet<Address>(user.getAddresses());
        this.falseAttemptCount=user.getFalseAttemptCount();
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public boolean isLocked() {
        return isLocked;
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

    public List<GrantAuthorityImpl> getGrantAuthorities() {
        return grantAuthorities;
    }

    public void setGrantAuthorities(List<GrantAuthorityImpl> grantAuthorities) {
        this.grantAuthorities = grantAuthorities;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
