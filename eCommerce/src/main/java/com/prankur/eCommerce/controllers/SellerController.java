package com.prankur.eCommerce.controllers;

import com.prankur.eCommerce.dtos.AddressDto;
import com.prankur.eCommerce.dtos.CustomerRegistrationDTO;
import com.prankur.eCommerce.dtos.PasswordResetDto;
import com.prankur.eCommerce.dtos.SellerRegistrationDTO;
import com.prankur.eCommerce.models.Seller;
import com.prankur.eCommerce.models.User;
import com.prankur.eCommerce.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

@RestController
@RequestMapping("seller")
public class SellerController
{
    @Autowired
    SellerService sellerService;

    @GetMapping("/home")
    public String sellerHome()
    {
        return "Seller home";
    }

    @PostMapping("/register")
    ResponseEntity<String> register(@Valid @RequestBody SellerRegistrationDTO sellerRegistrationDTO, HttpServletRequest httpServletRequest)
    {
        ResponseEntity<String> responseEntity = null;
        Locale locale = httpServletRequest.getLocale();
        Seller seller= sellerService.createSellerAccount(sellerRegistrationDTO);
        sellerService.triggerSellerRegistrationConfirmationEmail(seller,locale);
        responseEntity = ResponseEntity.status(HttpStatus.OK).body("Registration for Seller Successful.");
        System.out.printf("Seller Registered.");
        return responseEntity;
    }

    @GetMapping("/profile")
    public MappingJacksonValue customerProfile()
    {
        MappingJacksonValue mappingJacksonValue = sellerService.returnProfile();
        return mappingJacksonValue;
    }

    @PostMapping("/profileUpdate")
    public String updateProfile(@RequestBody SellerRegistrationDTO sellerRegistrationDTO)
    {
        String response =null;
        response= sellerService.updateProfile(sellerRegistrationDTO);
        return response;
    }

    @PostMapping("/passwordUpdate")
    public String updatePassword(@Valid @RequestBody PasswordResetDto passwordResetDto)
    {
        String response =null;
        response= sellerService.updatePassword(passwordResetDto);
        return response;
    }

    @PostMapping("/updateAddress")
    public String updateAddress(@RequestBody AddressDto addressDto)
    {
        String response =null;
        response= sellerService.updateAddress(addressDto);
        return response;
    }

}
