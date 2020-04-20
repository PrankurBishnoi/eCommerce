package com.prankur.eCommerce.services;

import com.prankur.eCommerce.dtos.PasswordResetDto;
import com.prankur.eCommerce.events.ForgotPasswordCompleteEvent;
import com.prankur.eCommerce.events.ResetPasswordEvent;
import com.prankur.eCommerce.models.users.User;
import com.prankur.eCommerce.models.VerificationToken;
import com.prankur.eCommerce.repositories.TokenRepository;
import com.prankur.eCommerce.repositories.UserRepos;
import com.prankur.eCommerce.security.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService
{
    @Autowired
    JdbcTokenStore jdbcTokenStore;

    @Autowired
    UserRepos userRepos;

    @Autowired
    TokenService tokenService;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    public void triggerForgotPassword(User user, String locale)
    {

        VerificationToken verificationToken = tokenService.createVerificationToken(user);
        String token = verificationToken.getToken();
        applicationEventPublisher.publishEvent(new ForgotPasswordCompleteEvent(user,token,locale));
    }

    public String resetPassword(String token, PasswordResetDto passwordResetDto, String appurl, String localeale)
    {
        String response = null;
        String password = passwordResetDto.getPassword();

        Optional<VerificationToken> optional = tokenRepository.findByTokenAndIsdeleted(token,false);
        if (optional.isPresent())
        {
            Calendar calendar = Calendar.getInstance();
            VerificationToken verificationToken = optional.get();
            Long tokenId = verificationToken.getId();
            User user = verificationToken.getUser();
            Date expiryTokenDate = verificationToken.getExpiryDate();
            long time = expiryTokenDate.getTime() - calendar.getTime().getTime();
            verificationToken.setIsdeleted(true);
            tokenRepository.save(verificationToken);
            System.out.println("Time for token  " + time);
            if (time<0)
            {
                System.out.println("Token has Expired");
                response = "Token has Expired";
            }
            else
            {
                user.setPassword(passwordEncoder.encode(password));
                user.setIsActive(true);
                userRepos.save(user);
                tokenRepository.deleteById(tokenId);
                response = "Password has been reset. Try to login";
                applicationEventPublisher.publishEvent(new ResetPasswordEvent(user));
                Collection<OAuth2AccessToken> collection = jdbcTokenStore.findTokensByUserName(user.getEmail());
                for(OAuth2AccessToken oAuth2AccessToken: collection)
                {
                    jdbcTokenStore
                            .removeAccessToken(oAuth2AccessToken);
                }
            }


        }
        else
        {
            response = "Token is not present in the Database. Please check the token";
//            throw new InvalidTokenException("Invalid Token");

        }
        return response;
    }



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
