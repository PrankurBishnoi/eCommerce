package com.prankur.eCommerce.services;

import com.prankur.eCommerce.models.User;
import com.prankur.eCommerce.models.VerificationToken;
import com.prankur.eCommerce.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService
{
    @Autowired
    TokenRepository tokenRepository;

    public VerificationToken createVerificationToken(User user)
    {
        String token = getToken();
        VerificationToken verificationToken = new VerificationToken(token,user);
        tokenRepository.save(verificationToken);

        return verificationToken;
    }

//    public void deleteAllExistingTokensForGivenUser(User user)
//    {
//        tokenRepository.deleteAllExistingTokenForGivenUser(user);
//    }

    private String getToken()
    {
        return UUID.randomUUID().toString();
    }


}
