package com.prankur.eCommerce.controllers;

import com.prankur.eCommerce.dtos.CustomerRegistrationDTO;
import com.prankur.eCommerce.models.Customer;
import com.prankur.eCommerce.models.User;
import com.prankur.eCommerce.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("customer")
public class CustomerController
{
    @Autowired
    CustomerService customerService;



    @PostMapping("/register")
    ResponseEntity<String> register(@Valid @RequestBody CustomerRegistrationDTO customerRegistrationDTO, HttpServletRequest httpServletRequest)
    {
        Locale locale = httpServletRequest.getLocale();
        ResponseEntity<String> responseEntity = null;
        System.out.println(customerRegistrationDTO);
        Customer customer = customerService.createCustomerAccount(customerRegistrationDTO);
        String appUrl = httpServletRequest.getContextPath();
        customerService.triggerCustomerRegistrationConfirmationEmail(appUrl,customer,locale);
        responseEntity = ResponseEntity.status(HttpStatus.OK).body("Registration for Customer Successful,    " + customerService.getVerificationLink(customer));
        System.out.println(customer);
        System.out.println("Customer Registered");

        return responseEntity;
    }

    @GetMapping("/registrationConfirmation")
    public String activateCustomer(@RequestParam("token") String token, WebRequest request)
    {
        System.out.printf("20");
        System.out.println(token);
        String response = customerService.registrationConfirmation(token,request.getContextPath(),request.getLocale());
        return response;
    }







//    @GetMapping("/all")
//    public List<Customer> retrieveAllCustomers()
//    {
//        return customerService.retrieveAllCustomers();
//    }

//    @GetMapping("/all")
//    public List<User> retrieveAllUsers()
//    {
//        return customerService.retrieveAllUser();
//    }
}
