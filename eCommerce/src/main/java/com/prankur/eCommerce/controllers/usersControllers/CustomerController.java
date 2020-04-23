package com.prankur.eCommerce.controllers.usersControllers;

import com.prankur.eCommerce.cos.AddressCO;
import com.prankur.eCommerce.cos.CustomerRegistrationCO;
import com.prankur.eCommerce.cos.EmailCO;
import com.prankur.eCommerce.cos.PasswordResetCO;
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
    ResponseEntity<String> register(@Valid @RequestBody CustomerRegistrationCO customerRegistrationCO, HttpServletRequest httpServletRequest)
    {
        Locale locale = httpServletRequest.getLocale();
        ResponseEntity<String> responseEntity = null;
        System.out.println(customerRegistrationCO);
        Customer customer = customerService.createCustomerAccount(customerRegistrationCO);
        String appUrl = httpServletRequest.getContextPath();
        customerService.triggerCustomerRegistrationConfirmationEmail(appUrl,customer,locale);
        responseEntity = ResponseEntity.status(HttpStatus.CREATED).body("Registration for Customer Successful,    " + customerService.getVerificationLink(customer));
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
    public ResponseEntity<String> resendVerificationCode(@Valid @RequestBody EmailCO emailCO, HttpServletRequest httpServletRequest)
    {
        ResponseEntity<String> responseEntity = null;
        Locale locale = httpServletRequest.getLocale();
        String response = customerService.resendVerificationLink(emailCO,locale);
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
    public String updateProfile(@RequestBody CustomerRegistrationCO customerRegistrationCO)
    {
        String response =null;
        response= customerService.updateProfile(customerRegistrationCO);
        return response;
    }

    @PostMapping("/passwordUpdate")
    public String updatePassword(@Valid @RequestBody PasswordResetCO passwordResetCO)
    {
        String response =null;
        response= customerService.updatePassword(passwordResetCO);
        return response;
    }

    @PostMapping("/addAddress")
    public String addAddress(@RequestBody AddressCO addressCO)
    {
        String response =null;
        response= customerService.addAddress(addressCO);
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
    public String updateAddress(@RequestBody AddressCO addressCO)
    {
        String response =null;
        response= customerService.updateAddress(addressCO);
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
