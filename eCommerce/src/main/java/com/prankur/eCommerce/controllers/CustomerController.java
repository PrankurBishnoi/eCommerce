package com.prankur.eCommerce.controllers;

import com.prankur.eCommerce.cos.AddressCO;
import com.prankur.eCommerce.cos.CustomerRegistrationCO;
import com.prankur.eCommerce.cos.EmailCO;
import com.prankur.eCommerce.cos.PasswordResetCO;
import com.prankur.eCommerce.dtos.Response;
import com.prankur.eCommerce.models.users.Customer;
import com.prankur.eCommerce.services.usersServices.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

@RestController
@RequestMapping("customer")
public class CustomerController
{
    @Autowired
    CustomerService customerService;

    @GetMapping("/home")
    public String customerHome()
    {
        return "customer home";
    }

    @PostMapping("/register")
    ResponseEntity<Response> register(@Valid @RequestBody CustomerRegistrationCO customerRegistrationCO, HttpServletRequest httpServletRequest)
    {
        Locale locale = httpServletRequest.getLocale();
        ResponseEntity<Response> responseEntity = null;
        Response response = new Response();
        System.out.println(customerRegistrationCO);
        Customer customer = customerService.createCustomerAccount(customerRegistrationCO);
        String appUrl = httpServletRequest.getContextPath();
        customerService.triggerCustomerRegistrationConfirmationEmail(appUrl,customer,locale);
        response.setResponseMessage("Registration for Customer Successful,    " + customerService.getVerificationLink(customer));
        responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);
        System.out.println(customer);
        System.out.println("Customer Registered");

        return responseEntity;
    }

    @GetMapping("/registrationConfirmation")
    ResponseEntity<Response> activateCustomer(@RequestParam("token") String token, WebRequest request)
    {
        ResponseEntity<Response> responseEntity = null;
        System.out.printf("20");
        System.out.println(token);
        Response response = new Response();
        response.setResponseMessage(customerService.registrationConfirmation(token,request.getContextPath(),request.getLocale()));
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(response);
        return responseEntity;
    }

    @PostMapping("/resendVerificationLink")
    public ResponseEntity<Response> resendVerificationCode(@Valid @RequestBody EmailCO emailCO, HttpServletRequest httpServletRequest)
    {
        ResponseEntity<Response> responseEntity = null;
        Locale locale = httpServletRequest.getLocale();
        Response response = new Response();
        response.setResponseMessage(customerService.resendVerificationLink(emailCO,locale));
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
    public MappingJacksonValue returnCustomerAddress()
    {
        MappingJacksonValue addresses = customerService.returnAddress();
        return addresses;
    }

    @PostMapping("/profileUpdate")
    public Response updateProfile(@RequestBody CustomerRegistrationCO customerRegistrationCO)
    {
        Response response =new Response();
        response.setResponseMessage(customerService.updateProfile(customerRegistrationCO));
        return response;
    }

    @PostMapping("/passwordUpdate")
    public Response updatePassword(@Valid @RequestBody PasswordResetCO passwordResetCO)
    {
        Response response =new Response();
        response.setResponseMessage(customerService.updatePassword(passwordResetCO));
        return response;
    }

    @PostMapping("/addAddress")
    public Response addAddress(@RequestBody AddressCO addressCO)
    {
        Response response =new Response();
        response.setResponseMessage(customerService.addAddress(addressCO));
        return response;
    }

    @PostMapping("/deleteAddress/{id}")
    public Response deleteAddress(@PathVariable Long addressId)
    {
        Response response =new Response();
        response.setResponseMessage(customerService.deleteAddress(addressId));
        return response;
    }

    @PostMapping("/updateAddress")
    public Response updateAddress(@RequestBody AddressCO addressCO)
    {
        Response response =new Response();
        response.setResponseMessage(customerService.updateAddress(addressCO));
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
