package com.prankur.eCommerce.services;

import com.prankur.eCommerce.dtos.CustomerRegistrationDTO;
import com.prankur.eCommerce.dtos.EmailDTO;
import com.prankur.eCommerce.enums.Roles;
import com.prankur.eCommerce.events.OnCustomerRegistrationEmailEvent;
import com.prankur.eCommerce.exceptions.InvalidTokenException;
import com.prankur.eCommerce.exceptions.ResourceAlreadyExistException;
import com.prankur.eCommerce.models.*;
import com.prankur.eCommerce.repositories.CustomerRepos;
import com.prankur.eCommerce.repositories.TokenRepository;
import com.prankur.eCommerce.repositories.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerService
{
    @Autowired
    UserRepos userRepos;

    @Autowired
    CustomerRepos customerRepos;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TokenService tokenService;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    public Customer createCustomerAccount(CustomerRegistrationDTO customerRegistrationDTO)
    {
        Customer response = null;
        Boolean doesCustomerExist = userRepos.existsByEmail(customerRegistrationDTO.getEmail());
        if (doesCustomerExist)
            throw new ResourceAlreadyExistException("Email Already Exists");
        Customer customer = new Customer(customerRegistrationDTO.getEmail(),
                                            customerRegistrationDTO.getFirstName(),customerRegistrationDTO.getMiddleName(),customerRegistrationDTO.getLastName(),
                                            passwordEncoder.encode(customerRegistrationDTO.getPassword()),
                                            true,false,
                                            customerRegistrationDTO.getAddress(),
                                            Arrays.asList(new GrantAuthorityImpl(Roles.CUSTOMER.getRoles())),
                                            false,false,false,false,0,
                                            customerRegistrationDTO.getContact()
                                        );

        if (customerRegistrationDTO.getAddress()!=null) {
            for (Address address : customerRegistrationDTO.getAddress())
            {
                customer.addAddress(address);
            }

//            Iterator<Address> i = customerRegistrationDTO.getAddress().iterator();
//            while (i.hasNext())
//                customer.addAddress(i.next());
        }
        userRepos.save(customer);
        response = customer;
        return response;

    }


    public void triggerCustomerRegistrationConfirmationEmail(String appUrl, User user, Locale locale)
    {
        VerificationToken verificationToken = tokenService.createVerificationToken(user);
        String token = verificationToken.getToken();
        System.out.println(token);
        applicationEventPublisher.publishEvent(new OnCustomerRegistrationEmailEvent(user,token,appUrl,locale));
    }

    public String registrationConfirmation(String token, String appUrl, Locale locale)
    {
        String response = null;

//        Optional<VerificationToken> verificationTokenDatas = tokenRepository.findByTokenAndIsdeleted(token,false);
        Optional<VerificationToken> verificationTokenDatas = tokenRepository.findByToken(token);

//        System.out.println(verificationTokenData.getToken());
        if(verificationTokenDatas.isPresent())
        {
            System.out.println("Verification token is Present");
            VerificationToken verificationTokenData = verificationTokenDatas.get();
            System.out.println(verificationTokenData.getExpiryDate());
            if (verificationTokenData.getIsdeleted()==false) {
                Calendar calendar = Calendar.getInstance();
                User user = verificationTokenData.getUser();
                Date expiryDaeOfToken = verificationTokenData.getExpiryDate();
                long temp = expiryDaeOfToken.getTime() - calendar.getTime().getTime();
                verificationTokenData.setIsdeleted(true);
                tokenRepository.save(verificationTokenData);
                System.out.println("time remaining to expiration of Token: " + temp);

                if (temp < 0) {
                    System.out.println("Expired Token");
                    triggerCustomerRegistrationConfirmationEmail(appUrl, user, locale);
                    throw new InvalidTokenException("Your Token has already expired, please check mail for new Token");
                } else {
                    System.out.println("Account Activated");
                    user.setIsActive(true);
                    userRepos.save(user);
                    response = "Your account has been activated";
                }
            }
            else
            {
                return response="Account Already Activated";
            }
        }
        else
        {
            System.out.println("Invalid Token");
            throw new InvalidTokenException("Invalid Token");
        }
        return response;
    }

    public String resendVerificationLink(EmailDTO emailDTO, Locale locale)
    {
        String response = null;

        User user = userRepos.findByEmail(emailDTO.getEmail());
        if (user.getIsActive()==true)
            response = "Account is Already Activated";
        else
        {
            tokenRepository.deleteAllExistingTokenForGivenUser(user);
            tokenService.createVerificationToken(user);
            triggerCustomerRegistrationConfirmationEmail("",user,locale);
            response = "A new Activation link has been sent to your email";

        }

        return response;
    }




    public String getVerificationLink(User user) {
        return user.getVerificationMail();
    }




//    public List<Customer> retrieveAllCustomers(){
//        return customerRepos.findAll();
//    }
//
//    public List<User> retrieveAllUser(){
//        return userRepos.findAll();
//    }

}
