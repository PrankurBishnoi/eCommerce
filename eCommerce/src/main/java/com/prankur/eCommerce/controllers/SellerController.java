package com.prankur.eCommerce.controllers;

import com.prankur.eCommerce.cos.AddressCO;
import com.prankur.eCommerce.cos.PasswordResetCO;
import com.prankur.eCommerce.cos.SellerRegistrationCO;
import com.prankur.eCommerce.models.users.Seller;
import com.prankur.eCommerce.services.usersServices.SellerService;
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
    ResponseEntity<String> register(@Valid @RequestBody SellerRegistrationCO sellerRegistrationCO, HttpServletRequest httpServletRequest)
    {
        ResponseEntity<String> responseEntity = null;
        Locale locale = httpServletRequest.getLocale();
        Seller seller= sellerService.createSellerAccount(sellerRegistrationCO);
        sellerService.triggerSellerRegistrationConfirmationEmail(seller,locale);
        responseEntity = ResponseEntity.status(HttpStatus.CREATED).body("Registration for Seller Successful.");
        System.out.printf("Seller Registered.");
        return responseEntity;
    }

    @GetMapping("/profile")
    public MappingJacksonValue customerProfile()
    {
        MappingJacksonValue mappingJacksonValue = sellerService.returnProfile();
        return mappingJacksonValue;
    }

    @GetMapping("/address")
    public MappingJacksonValue returnSellerAddress()
    {
        MappingJacksonValue addresses = sellerService.returnAddress();
        return addresses;
    }

    @PostMapping("/profileUpdate")
    public String updateProfile(@RequestBody SellerRegistrationCO sellerRegistrationCO)
    {
        String response =null;
        response= sellerService.updateProfile(sellerRegistrationCO);
        return response;
    }

    @PostMapping("/passwordUpdate")
    public String updatePassword(@Valid @RequestBody PasswordResetCO passwordResetCO)
    {
        String response =null;
        response= sellerService.updatePassword(passwordResetCO);
        return response;
    }

    @PostMapping("/addAddress")
    public String addAddress(@RequestBody AddressCO addressCO)
    {
        String response =null;
        response= sellerService.addAddress(addressCO);
        return response;
    }

    @PostMapping("/deleteAddress/{id}")
    public String deleteAddress(@PathVariable Long addressId)
    {
        String response =null;
        response= sellerService.deleteAddress(addressId);
        return response;
    }

    @PostMapping("/updateAddress")
    public String updateAddress(@RequestBody AddressCO addressCO)
    {
        String response =null;
        response= sellerService.updateAddress(addressCO);
        return response;
    }

}
