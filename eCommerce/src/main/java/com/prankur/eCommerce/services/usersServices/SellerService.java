package com.prankur.eCommerce.services.usersServices;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.prankur.eCommerce.cos.AddressCO;
import com.prankur.eCommerce.cos.PasswordResetCO;
import com.prankur.eCommerce.cos.SellerRegistrationCO;
import com.prankur.eCommerce.enums.Role;
import com.prankur.eCommerce.events.OnSellerRegistrationEmailEvent;
import com.prankur.eCommerce.exceptions.customExceptions.ResourceAlreadyExistException;
import com.prankur.eCommerce.models.*;
import com.prankur.eCommerce.models.users.Seller;
import com.prankur.eCommerce.models.users.User;
import com.prankur.eCommerce.repositories.AddressRepository;
import com.prankur.eCommerce.repositories.RolesRepository;
import com.prankur.eCommerce.repositories.usersRepositories.SellerRepository;
import com.prankur.eCommerce.repositories.TokenRepository;
import com.prankur.eCommerce.repositories.usersRepositories.UserRepository;
import com.prankur.eCommerce.security.AppUser;
import com.prankur.eCommerce.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

@Service
public class SellerService
{
    @Autowired
    UserRepository userRepository;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    RolesRepository rolesRepository;
    @Autowired
    TokenService tokenService;
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    PasswordEncoder passwordEncoder;

    public Seller createSellerAccount(SellerRegistrationCO sellerRegistrationCO)
    {
        Seller response = null;
        Boolean doesEmailExist = userRepository.existsByEmail(sellerRegistrationCO.getEmail());
        Boolean doesGSTExist = sellerRepository.existsByGst(sellerRegistrationCO.getGst());
        Boolean doesCompanyNameExist = sellerRepository.existsByCompanyNameIgnoreCase(sellerRegistrationCO.getCompanyName());

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

        Seller seller = new Seller(sellerRegistrationCO.getEmail(),
                                    sellerRegistrationCO.getFirstName(),sellerRegistrationCO.getMiddleName(),sellerRegistrationCO.getLastName(),
                                    passwordEncoder.encode(sellerRegistrationCO.getPassword()),
                                    false,false,
                                    sellerRegistrationCO.getAddress(),
                                    Arrays.asList(rolesRepository.findByAuthority(Role.SELLER.getRoles())),
                                    false,false,false,false,0,
                                    sellerRegistrationCO.getGst(),
                                    sellerRegistrationCO.getCompanyContact(),
                                    sellerRegistrationCO.getCompanyName()
                                  );

        if (sellerRegistrationCO.getAddress()!=null)
        {
            for (Address address : sellerRegistrationCO.getAddress()) {
                seller.addAddress(address);
            }
        }
        sellerRepository.save(seller);
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

    public MappingJacksonValue returnAddress()
    {
        Seller seller = giveCurrentLoggedInSeller();
        System.out.println("Customer in give address: "+seller);
        Set<Address> addresses1 = seller.getAddresses();
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("city","state","country","label","zipCode");
        SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();
        simpleFilterProvider.addFilter("AddressFilter",simpleBeanPropertyFilter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(addresses1);
        System.out.println(addresses1);
        mappingJacksonValue.setFilters(simpleFilterProvider);
        System.out.println("Seller Filter address " + mappingJacksonValue.getValue()+"   "+seller.getAddresses());
        return mappingJacksonValue;
    }

    public String updateProfile(SellerRegistrationCO sellerRegistrationCO)
    {
        String response = null;
        Seller seller = giveCurrentLoggedInSeller();
        if (sellerRegistrationCO.getFirstName()!=null)
            seller.setFirstName(sellerRegistrationCO.getFirstName());

        if (sellerRegistrationCO.getMiddleName()!=null)
            seller.setMiddleName(sellerRegistrationCO.getMiddleName());

        if (sellerRegistrationCO.getCompanyName()!=null)
            seller.setCompanyName(sellerRegistrationCO.getCompanyName());

        if (sellerRegistrationCO.getLastName()!=null)
            seller.setLastName(sellerRegistrationCO.getLastName());

//        if (sellerRegistrationCO.getCompanyContact()!=null)
//            seller.setCompanyContact(sellerRegistrationCO.getCompanyContact());

        if (sellerRegistrationCO.getGst()!=null)
            seller.setGst(sellerRegistrationCO.getGst());

        sellerRepository.save(seller);
        return response = "Profile Updated";
    }

    public String updatePassword(PasswordResetCO passwordResetCO)
    {
        String response = null;
        Seller seller = giveCurrentLoggedInSeller();
        String password = passwordResetCO.getPassword();
        seller.setPassword(password);
        sellerRepository.save(seller);
        response = "Password Updated";
        return response;
    }

    public String addAddress(AddressCO addressCO)
    {
        Seller seller = giveCurrentLoggedInSeller();
        Address address = new Address(addressCO.getCity(), addressCO.getState(), addressCO.getCountry(), addressCO.getAddressLine(), addressCO.getZipCode(), addressCO.getLabel());
        seller.addAddress(address);
        sellerRepository.save(seller);
        return "Address Saved";
    }

    public String deleteAddress(Long addressId)
    {
        String response = null;
        Seller seller = giveCurrentLoggedInSeller();
        Set<Address> addresses = seller.getAddresses();
        Iterator iterator = addresses.iterator();
        while(iterator.hasNext())
        {
            Address address = (Address) iterator.next();
            if (address.getId()==addressId)
            {
                addresses.remove(address);
                System.out.println(address);
                seller.setAddresses(addresses);
                sellerRepository.save(seller);
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
        Seller seller = giveCurrentLoggedInSeller();
        Address address = null;
        Set<Address> addresses = seller.getAddresses();
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

        addressRepository.save(address);
        return "ADDRESS UPDATED";

    }





    public Seller giveCurrentLoggedInSeller()
    {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        AppUser appUser = (AppUser) securityContext.getAuthentication().getPrincipal();
        System.out.println(appUser);
        String email = appUser.getEmail();
        Seller seller = sellerRepository.findByEmail(email);
        return seller;
    }


}
