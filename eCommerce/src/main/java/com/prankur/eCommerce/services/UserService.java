package com.prankur.eCommerce.services;

import com.prankur.eCommerce.models.User;
import com.prankur.eCommerce.repositories.UserRepos;
import com.prankur.eCommerce.security.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService
{
    @Autowired
    JdbcTokenStore jdbcTokenStore;

    @Autowired
    UserRepos userRepos;

    public void removeAllAccessTokenOfGivenUser(User user)
    {
        System.out.println("remove all access token for this user");
        Collection<OAuth2AccessToken> list = jdbcTokenStore.findTokensByUserName(user.getEmail());
        for (OAuth2AccessToken oAuth2AccessToken:list)
        {
            jdbcTokenStore.removeAccessToken(oAuth2AccessToken);
        }
    }

    public User giveCurrentLoggedInUser()
    {
        User user = null;
        SecurityContext securityContext = SecurityContextHolder.getContext();
        AppUser appUser = (AppUser) securityContext.getAuthentication().getPrincipal();
        System.out.println(appUser.getEmail() + "   "+ appUser.getAddresses());
        String email = appUser.getEmail();
        user = userRepos.findByEmail(email);
        System.out.println(user.getAddresses());

        return user;
    }
}
