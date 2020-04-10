package com.prankur.eCommerce.services;

import com.prankur.eCommerce.models.User;
import com.prankur.eCommerce.repositories.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    UserRepos userRepos;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepos.findByEmail(email);
        if(user==null)
            throw new UsernameNotFoundException("User not found in database!");
        return user;
    }
}
