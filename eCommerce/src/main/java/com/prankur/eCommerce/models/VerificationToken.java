package com.prankur.eCommerce.models;

import javax.persistence.*;
import java.util.Calendar;
import java.sql.Date;

@Entity
public class VerificationToken
{
    private static final int EXPIRATION = 60*24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Date expiryDate;

    private Boolean isdeleted;

    public VerificationToken(String token) {
        this.token = token;
        isdeleted = false;
        calculateExpiryDate();
    }

    public VerificationToken(String token, User user) {
        this.token = token;
        this.user = user;
        isdeleted = false;
        calculateExpiryDate();
    }

    public int getEXPIRATION() {
        return EXPIRATION;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean deleted) {
        isdeleted = deleted;
    }

    void calculateExpiryDate()
    {
        Calendar calender = Calendar.getInstance();
        calender.setTimeInMillis(new java.util.Date().getTime());
        calender.add(Calendar.MINUTE,this.getEXPIRATION());
        expiryDate = new Date(calender.getTime().getTime());
    }

    public VerificationToken() {

    }
}
