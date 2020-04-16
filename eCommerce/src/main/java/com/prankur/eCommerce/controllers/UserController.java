package com.prankur.eCommerce.controllers;

import com.prankur.eCommerce.dtos.PasswordResetDto;
import com.prankur.eCommerce.models.User;
import com.prankur.eCommerce.repositories.UserRepos;
import com.prankur.eCommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

@RestController
@RequestMapping("user")
public class UserController
{
    @Autowired
    TokenStore tokenStore;

    @Autowired
    UserRepos userRepos;

    @Autowired
    UserService userService;

    @PostMapping("/forgotPassword/{email}")
    public String forgotPassword(@PathVariable String email, HttpServletRequest httpServletRequest)
    {
        String locale = httpServletRequest.getLocale().toString();
        User user = userRepos.findByEmail(email);
        if(user!=null)
        {
            System.out.println(user);
            userService.triggerForgotPassword(user,locale);
            return "Email to reset password has been sent";
        }
        else
            return "Email Not Found";
    }

    @PostMapping("/resetPassword")
    String resetPassword(@RequestParam("token")String token, @Valid @RequestBody PasswordResetDto passwordResetDto,HttpServletRequest httpServletRequest)
    {
//        ResponseEntity<String> responseEntity = null;
        String response = userService.resetPassword(token,passwordResetDto,httpServletRequest.getContextPath(),httpServletRequest.getLocale().toString());
//        responseEntity = ResponseEntity.status(HttpStatus.OK).body(response);
        return  response;
    }




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
