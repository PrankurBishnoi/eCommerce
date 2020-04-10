package com.prankur.eCommerce.controllers;

import com.prankur.eCommerce.dtos.CustomerRegistrationDTO;
import com.prankur.eCommerce.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("customer")
public class CustomerController
{
    @Autowired
    CustomerService customerService;

    @PostMapping("/register")
    ResponseEntity register(@Valid @RequestBody CustomerRegistrationDTO customerRegistrationDTO)
    {
        ResponseEntity<Object> responseEntity = null;

        customerService.createCustomerAccount(customerRegistrationDTO);

        responseEntity = new ResponseEntity<Object>(HttpStatus.OK);

        System.out.println("Customer Registered");

        return responseEntity;
    }

}
