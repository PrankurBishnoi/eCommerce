package com.prankur.eCommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prankur.eCommerce.models.users.User;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
public class Roles implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long roleId;

    private String authority;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public Roles(String authority) {
        this.authority = authority;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Roles{" +
                "roleId=" + roleId +
                ", authority='" + authority + '\'' +
                ", users=" + users +
                '}';
    }

    public Roles() {
    }
}
