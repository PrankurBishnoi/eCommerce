package com.prankur.eCommerce.controllers;

import com.prankur.eCommerce.dtos.CustomerRegistrationDTO;
import com.prankur.eCommerce.models.Customer;
import com.prankur.eCommerce.models.User;
import com.prankur.eCommerce.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController
{
    @Autowired
    CustomerService customerService;

//    @GetMapping("/all")
//    public List<Customer> retrieveAllCustomers()
//    {
//        return customerService.retrieveAllCustomers();
//    }

    @GetMapping("/all")
    public List<User> retrieveAllUsers()
    {
        return customerService.retrieveAllUser();
    }

    @PostMapping("/register")
    Customer register(@Valid @RequestBody CustomerRegistrationDTO customerRegistrationDTO)
    {

        //ResponseEntity<Object> responseEntity = null;
        System.out.println(customerRegistrationDTO);
        Customer customer = customerService.createCustomerAccount(customerRegistrationDTO);
       // responseEntity = new ResponseEntity<Object>(HttpStatus.OK);
        System.out.println(customer);
        System.out.println("Customer Registered");

        return customer;
    }

}
