package com.prankur.eCommerce.services;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.prankur.eCommerce.dtos.AddressDto;
import com.prankur.eCommerce.dtos.CustomerRegistrationDTO;
import com.prankur.eCommerce.dtos.PasswordResetDto;
import com.prankur.eCommerce.dtos.SellerRegistrationDTO;
import com.prankur.eCommerce.enums.Roles;
import com.prankur.eCommerce.events.OnSellerRegistrationEmailEvent;
import com.prankur.eCommerce.exceptions.ResourceAlreadyExistException;
import com.prankur.eCommerce.models.*;
import com.prankur.eCommerce.repositories.AddressRepos;
import com.prankur.eCommerce.repositories.SellerRepos;
import com.prankur.eCommerce.repositories.TokenRepository;
import com.prankur.eCommerce.repositories.UserRepos;
import com.prankur.eCommerce.security.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;

@Service
public class SellerService
{
    @Autowired
    UserRepos userRepos;

    @Autowired
    SellerRepos sellerRepos;

    @Autowired
    AddressRepos addressRepos;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Seller createSellerAccount(SellerRegistrationDTO sellerRegistrationDTO)
    {
        Seller response = null;
        Boolean doesEmailExist = userRepos.existsByEmail(sellerRegistrationDTO.getEmail());
        Boolean doesGSTExist = sellerRepos.existsByGst(sellerRegistrationDTO.getGst());
        Boolean doesCompanyNameExist = sellerRepos.existsByCompanyNameIgnoreCase(sellerRegistrationDTO.getCompanyName());

        int flag = 0;
        if (doesEmailExist)
            flag = flag+1;
        if (doesCompanyNameExist)
            flag = flag+2;
        if (doesGSTExist)
            flag = flag+4;
        if(flag == 1)
            throw  new ResourceAlreadyExistException("Email Already Exists");
        else if (flag == 2)
            throw new ResourceAlreadyExistException("Company Name Already Exists");
        else if(flag == 3)
            throw  new ResourceAlreadyExistException("Email , CompanyName Already Exists");
        else if (flag == 4)
            throw new ResourceAlreadyExistException("GST number Already Exists");
        else if (flag == 5)
            throw  new ResourceAlreadyExistException("Email and GST Already Exists");
        else if(flag == 6)
            throw  new ResourceAlreadyExistException("GST , CompanyName Already Exists");
        else if(flag == 7)
            throw  new ResourceAlreadyExistException("Email , GST , CompanyName Already Exists");

        Seller seller = new Seller(sellerRegistrationDTO.getEmail(),
                                    sellerRegistrationDTO.getFirstName(),sellerRegistrationDTO.getMiddleName(),sellerRegistrationDTO.getLastName(),
                                    passwordEncoder.encode(sellerRegistrationDTO.getPassword()),
                                    false,false,
                                    sellerRegistrationDTO.getAddress(),
                                    Arrays.asList(new GrantAuthorityImpl(Roles.SELLER.getRoles())),
                                    false,false,false,false,0,
                                    sellerRegistrationDTO.getGst(),
                                    sellerRegistrationDTO.getCompanyContact(),
                                    sellerRegistrationDTO.getCompanyName()
                                  );

        if (sellerRegistrationDTO.getAddress()!=null)
        {
            for (Address address : sellerRegistrationDTO.getAddress()) {
                seller.addAddress(address);
            }
        }
        sellerRepos.save(seller);
        response = seller;
        return response;


    }


    public void triggerSellerRegistrationConfirmationEmail(User user, Locale locale)
    {
        System.out.println(1);
        applicationEventPublisher.publishEvent(new OnSellerRegistrationEmailEvent(user,locale));
    }

    public MappingJacksonValue returnProfile()
    {
//        email = "%"+email+"%";
        Seller seller = giveCurrentLoggedInSeller();
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("id","firstName","middleName","lastName","isActive","contact","companyContact","companyName","gst");
        SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();
        simpleFilterProvider.addFilter("SellerFilter",simpleBeanPropertyFilter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(seller);
        mappingJacksonValue.setFilters(simpleFilterProvider);
        System.out.println("Seller Filter profile " + mappingJacksonValue.getValue());
        return mappingJacksonValue;

    }

    public String updateProfile(SellerRegistrationDTO sellerRegistrationDTO)
    {
        String response = null;
        Seller seller = giveCurrentLoggedInSeller();
        if (sellerRegistrationDTO.getFirstName()!=null)
            seller.setFirstName(sellerRegistrationDTO.getFirstName());

        if (sellerRegistrationDTO.getMiddleName()!=null)
            seller.setMiddleName(sellerRegistrationDTO.getMiddleName());

        if (sellerRegistrationDTO.getCompanyName()!=null)
            seller.setCompanyName(sellerRegistrationDTO.getCompanyName());

        if (sellerRegistrationDTO.getLastName()!=null)
            seller.setLastName(sellerRegistrationDTO.getLastName());

//        if (sellerRegistrationDTO.getCompanyContact()!=null)
//            seller.setCompanyContact(sellerRegistrationDTO.getCompanyContact());

        if (sellerRegistrationDTO.getGst()!=null)
            seller.setGst(sellerRegistrationDTO.getGst());

        sellerRepos.save(seller);
        return response = "Profile Updated";
    }

    public String updatePassword(PasswordResetDto passwordResetDto)
    {
        String response = null;
        Seller seller = giveCurrentLoggedInSeller();
        String password = passwordResetDto.getPassword();
        seller.setPassword(password);
        sellerRepos.save(seller);
        response = "Password Updated";
        return response;
    }

    public String updateAddress(AddressDto addressDto)
    {
        String response = null;
        Seller seller = giveCurrentLoggedInSeller();
        Address address = null;
        Set<Address> addresses = seller.getAddresses();
        for(Address a : addresses) {
            if (a.getId() == addressDto.getId()) {
                address = a;
            }
        }
        System.out.println(address);
        if (addressDto.getCity() != null) {
            String city = addressDto.getCity();
            address.setCity(city);
        }
        if (addressDto.getCountry() != null) {
            String country = addressDto.getCountry();
            address.setCountry(country);
        }
        if (addressDto.getLabel() != null) {
            String houseNumber = addressDto.getLabel();
            address.setCity(houseNumber);
        }
        if (addressDto.getState() != null) {
            String state = addressDto.getState();
            address.setCity(state);
        }
//        if (addressDto.getZipCode() != null) {
//            String pinCode = addressDto.getZipCode();
//            address.setCity(pinCode);
//        }

        addressRepos.save(address);
        return "ADDRESS UPDATED";

    }





    public Seller giveCurrentLoggedInSeller()
    {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        AppUser appUser = (AppUser) securityContext.getAuthentication().getPrincipal();
        System.out.println(appUser);
        String email = appUser.getEmail();
        Seller seller = sellerRepos.findByEmail(email);
        return seller;
    }


}
