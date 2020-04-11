package com.prankur.eCommerce.services;

import com.prankur.eCommerce.dtos.SellerRegistrationDTO;
import com.prankur.eCommerce.enums.Roles;
import com.prankur.eCommerce.exceptions.ResourceAlreadyExistException;
import com.prankur.eCommerce.models.Address;
import com.prankur.eCommerce.models.GrantAuthorityImpl;
import com.prankur.eCommerce.models.Seller;
import com.prankur.eCommerce.repositories.SellerRepos;
import com.prankur.eCommerce.repositories.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class SellerService
{
    @Autowired
    UserRepos userRepos;

    @Autowired
    SellerRepos sellerRepos;



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
                                    sellerRegistrationDTO.getPassword(),
                                    false,false,
                                    sellerRegistrationDTO.getAddress(),
                                    Arrays.asList(new GrantAuthorityImpl("ROLE_"+Roles.SELLER.toString())),
                                    true,true,true,false,0,
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



}
