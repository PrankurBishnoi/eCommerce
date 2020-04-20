package com.prankur.eCommerce.security;

import com.prankur.eCommerce.events.OnAccountLLockedEvent;
import com.prankur.eCommerce.models.users.User;
import com.prankur.eCommerce.repositories.UserRepos;
import com.prankur.eCommerce.services.UserService;
import com.prankur.eCommerce.utils.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import java.util.Collection;

public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider
{
    @Autowired
    JdbcTokenStore jdbcTokenStore;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder  passwordEncoder;

    @Autowired
    private UserRepos userRepos;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private UserService userService;

    @Value("${spring.mail.from.email}")
    private String fromEmail;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authenticationToken) throws AuthenticationException
    {
        if(authenticationToken.getCredentials()==null)
        {
            logger.debug("Authentication failed: no credentials provided");
            throw new BadCredentialsException("Bad Credentials: No credentials provided");

        }

        String presentedPassword = authenticationToken.getCredentials().toString();

        if(!passwordEncoder.matches(presentedPassword,userDetails.getPassword()))
        {
            logger.debug("Given password doesn't match with the database password");
            System.out.println("username from userdetails is "+userDetails.getUsername());
            User user = userRepos.findByEmail(userDetails.getUsername());
            Integer temp = user.getFalseAttemptCount();
            user.setFalseAttemptCount(++temp);
            if (temp>=3)
            {
                user.setLocked(true);
                System.out.println("more than 3 attempts");
                Collection<OAuth2AccessToken> list = jdbcTokenStore.findTokensByUserName(user.getEmail());
                for (OAuth2AccessToken oAuth2AccessToken :list)
                {
                    jdbcTokenStore.removeAccessToken(oAuth2AccessToken);
                }
//                userService.removeAllAccessTokenOfGivenUser(user);
                String message = "Your Account has been locked due to Too many login attempts..";
                Email email = new Email(fromEmail,user.getEmail(),"Account Locked",message);
                applicationEventPublisher.publishEvent(new OnAccountLLockedEvent(email));

            }
            userRepos.save(user);

            throw new BadCredentialsException("Bad Credentials");
        }


    }

    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails userDetails)
    {
        User user = userRepos.findByEmail(userDetails.getUsername());
        user.setFalseAttemptCount(0);
        userRepos.save(user);
        return super.createSuccessAuthentication(principal, authentication, userDetails);

    }


}
