package com.prankur.eCommerce.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class GrantAuthorityImpl
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long roleId;
    private String authority;

    @ManyToMany(mappedBy = "authorities")
    private List<User> users;

    public GrantAuthorityImpl(String authority) {
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
        return "GrantAuthorityImpl{" +
                "roleId=" + roleId +
                ", authority='" + authority + '\'' +
                ", users=" + users +
                '}';
    }
}
