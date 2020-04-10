package com.prankur.eCommerce.services;

import com.prankur.eCommerce.dtos.CustomerRegistrationDTO;
import com.prankur.eCommerce.enums.Roles;
import com.prankur.eCommerce.exceptions.ResourceAlreadyExistException;
import com.prankur.eCommerce.models.Address;
import com.prankur.eCommerce.models.Customer;
import com.prankur.eCommerce.models.GrantAuthorityImpl;
import com.prankur.eCommerce.repositories.CustomerRepos;
import com.prankur.eCommerce.repositories.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class CustomerService
{
    @Autowired
    UserRepos userRepos;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Customer createCustomerAccount(CustomerRegistrationDTO customerRegistrationDTO)
    {
        Customer response = null;
        Boolean doesCustomerExist = userRepos.existsByEmail(customerRegistrationDTO.getEmail());
        if (doesCustomerExist)
            throw new ResourceAlreadyExistException("Email Already Exists");
        Customer customer = new Customer(customerRegistrationDTO.getEmail(),
                                            customerRegistrationDTO.getFirstName(),customerRegistrationDTO.getMiddleName(),customerRegistrationDTO.getLastName(),
                                            passwordEncoder.encode(customerRegistrationDTO.getPassword()),
                                            false,false,customerRegistrationDTO.getAddress(), Arrays.asList(new GrantAuthorityImpl(Roles.CUSTOMER.toString())),true,true,true,false,0,customerRegistrationDTO.getContact());
        for (Address address : customerRegistrationDTO.getAddress())
        {
            customer.addAddress(address);
        }
        userRepos.save(customer);
        response = customer;
        return response;

    }


}
