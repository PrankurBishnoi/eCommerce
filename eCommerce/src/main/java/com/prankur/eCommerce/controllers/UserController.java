package com.prankur.eCommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("user")
public class UserController
{
    @Autowired
    TokenStore tokenStore;

    @GetMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest)
    {
        String response = null;
        String authHeader = httpServletRequest.getHeader("Authorization");
        if(authHeader != null)
        {
            String tokenValue = authHeader.replace("Bearer","").trim();
            System.out.println(tokenValue);
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
            response = "LoggedOut Successfully";
        }
        else
            response = "Give Token by Authorization";
        return response;
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> loginCustomer(@Req)

}
