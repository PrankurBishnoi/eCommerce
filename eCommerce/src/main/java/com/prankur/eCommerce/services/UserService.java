package com.prankur.eCommerce.services;

import com.prankur.eCommerce.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService
{
    @Autowired
    JdbcTokenStore jdbcTokenStore;

    public void removeAllAccessTokenOfGivenUser(User user)
    {
        System.out.println("remove all access token for this user");
        Collection<OAuth2AccessToken> list = jdbcTokenStore.findTokensByUserName(user.getEmail());
        for (OAuth2AccessToken oAuth2AccessToken:list)
        {
            jdbcTokenStore.removeAccessToken(oAuth2AccessToken);
        }
    }
}
