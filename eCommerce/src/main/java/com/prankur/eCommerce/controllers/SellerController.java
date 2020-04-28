package com.prankur.eCommerce.controllers;

import com.prankur.eCommerce.cos.AddressCO;
import com.prankur.eCommerce.cos.PasswordResetCO;
import com.prankur.eCommerce.cos.SellerRegistrationCO;
import com.prankur.eCommerce.dtos.Response;
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
    ResponseEntity<Response> register(@Valid @RequestBody SellerRegistrationCO sellerRegistrationCO, HttpServletRequest httpServletRequest)
    {
        ResponseEntity<Response> responseEntity = null;
        Response response = new Response();
        Locale locale = httpServletRequest.getLocale();
        Seller seller= sellerService.createSellerAccount(sellerRegistrationCO);
        sellerService.triggerSellerRegistrationConfirmationEmail(seller,locale);
        response.setResponseMessage("Registration for Seller Successful.");
        responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);
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
    public Response updateProfile(@RequestBody SellerRegistrationCO sellerRegistrationCO)
    {
        Response response = new Response();
        response.setResponseMessage(sellerService.updateProfile(sellerRegistrationCO));
        return response;
    }

    @PostMapping("/passwordUpdate")
    public Response updatePassword(@Valid @RequestBody PasswordResetCO passwordResetCO)
    {
        Response response = new Response();
        response.setResponseMessage(sellerService.updatePassword(passwordResetCO));
        return response;
    }

    @PostMapping("/addAddress")
    public Response addAddress(@RequestBody AddressCO addressCO)
    {
        Response response = new Response();
        response.setResponseMessage(sellerService.addAddress(addressCO));
        return response;
    }

    @PostMapping("/deleteAddress/{id}")
    public Response deleteAddress(@PathVariable Long addressId)
    {
        Response response = new Response();
        response.setResponseMessage(sellerService.deleteAddress(addressId));
        return response;
    }

    @PostMapping("/updateAddress")
    public Response updateAddress(@RequestBody AddressCO addressCO)
    {
        Response response = new Response();
        response.setResponseMessage(sellerService.updateAddress(addressCO));
        return response;
    }

}
