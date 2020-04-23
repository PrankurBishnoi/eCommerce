package com.prankur.eCommerce.services.usersServices;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.prankur.eCommerce.cos.AddressCO;
import com.prankur.eCommerce.cos.CustomerRegistrationCO;
import com.prankur.eCommerce.cos.EmailCO;
import com.prankur.eCommerce.cos.PasswordResetCO;
import com.prankur.eCommerce.enums.Roles;
import com.prankur.eCommerce.events.OnCustomerRegistrationEmailEvent;
import com.prankur.eCommerce.exceptions.InvalidTokenException;
import com.prankur.eCommerce.exceptions.ResourceAlreadyExistException;
import com.prankur.eCommerce.models.*;
import com.prankur.eCommerce.models.users.Customer;
import com.prankur.eCommerce.models.users.User;
import com.prankur.eCommerce.repositories.AddressRepos;
import com.prankur.eCommerce.repositories.usersReposes.CustomerRepos;
import com.prankur.eCommerce.repositories.TokenRepository;
import com.prankur.eCommerce.repositories.usersReposes.UserRepos;
import com.prankur.eCommerce.security.AppUser;
import com.prankur.eCommerce.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerService
{
    @Autowired
    UserRepos userRepos;

    @Autowired
    AddressRepos addressRepos;

    @Autowired
    UserService userService;

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

    public Customer createCustomerAccount(CustomerRegistrationCO customerRegistrationCO)
    {
        Customer response = null;
        Boolean doesCustomerExist = userRepos.existsByEmail(customerRegistrationCO.getEmail());
//        System.out.println(customerRegistrationCO.getAddresses());
        if (doesCustomerExist)
            throw new ResourceAlreadyExistException("Email Already Exists");
        Customer customer = new Customer(customerRegistrationCO.getEmail(),
                                            customerRegistrationCO.getFirstName(),customerRegistrationCO.getMiddleName(),customerRegistrationCO.getLastName(),
                                            passwordEncoder.encode(customerRegistrationCO.getPassword()),
                                            true,false,
                                            customerRegistrationCO.getAddresses(),
                                            Arrays.asList(new GrantAuthorityImpl(Roles.CUSTOMER.getRoles())),
                                            false,false,false,false,0,
                                            customerRegistrationCO.getContact()
                                        );

        if (customerRegistrationCO.getAddresses()!=null)
        {
//            for (Address address : customerRegistrationCO.getAddresses())
//            {
//                customer.addAddress(address);
//            }

            Iterator<Address> i = customerRegistrationCO.getAddresses().iterator();
//            System.out.println(customerRegistrationCO.getAddresses());
            while (i.hasNext())
                customer.addAddress(i.next());
        }

//        Iterator<Address> i = addressess.iterator();
//        while(i.hasNext())
//            admin.addAddress(i.next());

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

    public String resendVerificationLink(EmailCO emailCO, Locale locale)
    {
        String response = null;

        User user = userRepos.findByEmail(emailCO.getEmail());
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

    public MappingJacksonValue returnProfile()
    {
//        email = "%"+email+"%";
        Customer customer = giveCurrentLoggedInCustomer();
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("id","firstName","middleName","lastName","isActive","contact");
        SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();
        simpleFilterProvider.addFilter("CustomerFilter",simpleBeanPropertyFilter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(customer);
        mappingJacksonValue.setFilters(simpleFilterProvider);
        System.out.println("Customer Filter profile " + mappingJacksonValue.getValue());
        return mappingJacksonValue;

    }

    public MappingJacksonValue returnAddress()
    {
//        User user = userService.giveCurrentLoggedInUser();
//        Optional<User> users = userRepos.findById(customer.getId());
//        User user = users.get();
//        Set<Address> addresses = user.getAddresses();
//        System.out.println("Customer of address "+ user);
//        System.out.println("Customer address "+addresses);
//        email = "%"+email+"%";
        Customer customer = giveCurrentLoggedInCustomer();
        System.out.println("Customer in give address: "+customer);
        Set<Address> addresses1 = customer.getAddresses();
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("city","state","country","label","zipCode");
        SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();
        simpleFilterProvider.addFilter("AddressFilter",simpleBeanPropertyFilter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(addresses1);
        System.out.println(addresses1);
        mappingJacksonValue.setFilters(simpleFilterProvider);
        System.out.println("Customer Filter address " + mappingJacksonValue.getValue()+"   "+customer.getAddresses());
        return mappingJacksonValue;

    }

    public String updateProfile(CustomerRegistrationCO customerRegistrationCO)
    {
        String response = null;
        Customer customer = giveCurrentLoggedInCustomer();
        if (customerRegistrationCO.getFirstName()!=null)
            customer.setFirstName(customerRegistrationCO.getFirstName());

        if (customerRegistrationCO.getMiddleName()!=null)
            customer.setMiddleName(customerRegistrationCO.getMiddleName());

        if (customerRegistrationCO.getLastName()!=null)
            customer.setLastName(customerRegistrationCO.getLastName());

        customerRepos.save(customer);
        return response = "Profile Updated";
    }

    public String updatePassword(PasswordResetCO passwordResetCO)
    {
        String response = null;
        Customer customer = giveCurrentLoggedInCustomer();
        String password = passwordResetCO.getPassword();
        customer.setPassword(password);
        customerRepos.save(customer);
        response = "Password Updated";
        return response;
    }

    public String addAddress(AddressCO addressCO)
    {
        Customer customer = giveCurrentLoggedInCustomer();
        Address address = new Address(addressCO.getCity(), addressCO.getState(), addressCO.getCountry(), addressCO.getAddressLine(), addressCO.getZipCode(), addressCO.getLabel());
        customer.addAddress(address);
        customerRepos.save(customer);
        return "Address Saved";
    }

    public String deleteAddress(Long addressId)
    {
        String response = null;
        Customer customer = giveCurrentLoggedInCustomer();
        Set<Address> addresses = customer.getAddresses();
        Iterator iterator = addresses.iterator();
        while(iterator.hasNext())
        {
            Address address = (Address) iterator.next();
            if (address.getId()==addressId)
            {
                addresses.remove(address);
                System.out.println(address);
                customer.setAddresses(addresses);
                customerRepos.save(customer);
                response = "Address Deleted";
                break;
            }
        }
        if (response ==null)
            response  = "Address not found";
        return response;
    }

    public String updateAddress(AddressCO addressCO)
    {
        String response = null;
        Customer customer = giveCurrentLoggedInCustomer();
        Address address = null;
        Set<Address> addresses = customer.getAddresses();
        for(Address a : addresses) {
            if (a.getId() == addressCO.getId()) {
                address = a;
            }
        }
        System.out.println(address);
        if (addressCO.getCity() != null) {
            String city = addressCO.getCity();
            address.setCity(city);
        }
        if (addressCO.getCountry() != null) {
            String country = addressCO.getCountry();
            address.setCountry(country);
        }
        if (addressCO.getLabel() != null) {
            String houseNumber = addressCO.getLabel();
            address.setCity(houseNumber);
        }
        if (addressCO.getState() != null) {
            String state = addressCO.getState();
            address.setCity(state);
        }
//        if (addressCO.getZipCode() != null) {
//            String pinCode = addressCO.getZipCode();
//            address.setCity(pinCode);
//        }

        addressRepos.save(address);
        return "ADDRESS UPDATED";

    }





    public Customer giveCurrentLoggedInCustomer()
    {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        AppUser appUser = (AppUser) securityContext.getAuthentication().getPrincipal();
        System.out.println(appUser);
        String email = appUser.getEmail();
        Customer customer = customerRepos.findByEmail(email);
        System.out.println("Current logged in customer" + email);
        return customer;
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
