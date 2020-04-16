package com.prankur.eCommerce.controllers;

import com.prankur.eCommerce.dtos.AddressDto;
import com.prankur.eCommerce.dtos.CustomerRegistrationDTO;
import com.prankur.eCommerce.dtos.EmailDTO;
import com.prankur.eCommerce.dtos.PasswordResetDto;
import com.prankur.eCommerce.models.Address;
import com.prankur.eCommerce.models.Customer;
import com.prankur.eCommerce.models.User;
import com.prankur.eCommerce.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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
    ResponseEntity<String> activateCustomer(@RequestParam("token") String token, WebRequest request)
    {
        ResponseEntity<String> responseEntity = null;
        System.out.printf("20");
        System.out.println(token);
        String response = customerService.registrationConfirmation(token,request.getContextPath(),request.getLocale());
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(response);
        return responseEntity;
    }

    @PostMapping("/resendVerificationLink")
    public ResponseEntity<String> resendVerificationCode(@Valid @RequestBody EmailDTO emailDTO,HttpServletRequest httpServletRequest)
    {
        ResponseEntity<String> responseEntity = null;
        Locale locale = httpServletRequest.getLocale();
        String response = customerService.resendVerificationLink(emailDTO,locale);
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(response);
        return responseEntity;
    }

    @GetMapping("/profile")
    public MappingJacksonValue customerProfile()
    {
        MappingJacksonValue mappingJacksonValue = customerService.returnProfile();
        return mappingJacksonValue;
    }

    @GetMapping("/address")
    public Set<Address> customerAddress()
    {
        Set<Address> addresses = customerService.returnAddress();
        return addresses;
    }

    @PostMapping("/profileUpdate")
    public String updateProfile(@RequestBody CustomerRegistrationDTO customerRegistrationDTO)
    {
        String response =null;
        response= customerService.updateProfile(customerRegistrationDTO);
        return response;
    }

    @PostMapping("/passwordUpdate")
    public String updatePassword(@Valid @RequestBody PasswordResetDto passwordResetDto)
    {
        String response =null;
        response= customerService.updatePassword(passwordResetDto);
        return response;
    }

    @PostMapping("/addAddress")
    public String addAddress(@RequestBody AddressDto addressDto)
    {
        String response =null;
        response= customerService.addAddress(addressDto);
        return response;
    }

    @PostMapping("/deleteAddress/{id}")
    public String deleteAddress(@PathVariable Long addressId)
    {
        String response =null;
        response= customerService.deleteAddress(addressId);
        return response;
    }

    @PostMapping("/updateAddress")
    public String updateAddress(@RequestBody AddressDto addressDto)
    {
        String response =null;
        response= customerService.updateAddress(addressDto);
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
