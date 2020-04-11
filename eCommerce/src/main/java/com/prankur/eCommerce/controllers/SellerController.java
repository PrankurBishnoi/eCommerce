package com.prankur.eCommerce.controllers;

import com.prankur.eCommerce.dtos.SellerRegistrationDTO;
import com.prankur.eCommerce.models.Seller;
import com.prankur.eCommerce.models.User;
import com.prankur.eCommerce.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("seller")
public class SellerController
{
    @Autowired
    SellerService sellerService;

    @PostMapping("/register")
    Seller register(@Valid @RequestBody SellerRegistrationDTO sellerRegistrationDTO)
    {
        Seller seller= sellerService.createSellerAccount(sellerRegistrationDTO);
        System.out.printf("Seller Registered.");
        return seller;
    }


}
