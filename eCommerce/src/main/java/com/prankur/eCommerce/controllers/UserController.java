package com.prankur.eCommerce.controllers;

import com.prankur.eCommerce.cos.PasswordResetCO;
import com.prankur.eCommerce.dtos.Response;
import com.prankur.eCommerce.models.users.User;
import com.prankur.eCommerce.repositories.usersRepositories.UserRepository;
import com.prankur.eCommerce.services.usersServices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("user")
public class UserController
{
    @Autowired
    TokenStore tokenStore;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @PostMapping("/forgotPassword/{email}")
    public Response forgotPassword(@PathVariable String email, HttpServletRequest httpServletRequest)
    {
        String locale = httpServletRequest.getLocale().toString();
        Response response = new Response();
        User user = userRepository.findByEmail(email);
        if(user!=null)
        {
            System.out.println(user);
            userService.triggerForgotPassword(user,locale);
            response.setResponseMessage("Email to reset password has been sent");
        }
        else
            response.setResponseMessage("Email Not Found");
        return response;
    }

    @PostMapping("/resetPassword")
    Response resetPassword(@RequestParam("token")String token, @Valid @RequestBody PasswordResetCO passwordResetCO, HttpServletRequest httpServletRequest)
    {
//        ResponseEntity<String> responseEntity = null;
        Response response = new Response();
        response.setResponseMessage(userService.resetPassword(token, passwordResetCO,httpServletRequest.getContextPath(),httpServletRequest.getLocale().toString()));
//        responseEntity = ResponseEntity.status(HttpStatus.OK).body(response);
        return  response;
    }




    @GetMapping("/logout")
    public Response logout(HttpServletRequest httpServletRequest)
    {
        Response response = new Response();
        String authHeader = httpServletRequest.getHeader("Authorization");
        if(authHeader != null)
        {
            String tokenValue = authHeader.replace("Bearer","").trim();
            System.out.println(tokenValue);
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
            response.setResponseMessage("LoggedOut Successfully");
        }
        else
            response.setResponseMessage("Give Token by Authorization");
        return response;
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> loginCustomer(@Req)

}
