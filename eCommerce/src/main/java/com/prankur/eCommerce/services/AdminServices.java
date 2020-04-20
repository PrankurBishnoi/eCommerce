package com.prankur.eCommerce.services;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.prankur.eCommerce.events.OnAccountActivationEvent;
import com.prankur.eCommerce.exceptions.ResourceNotFoundException;
import com.prankur.eCommerce.models.users.Customer;
import com.prankur.eCommerce.models.users.Seller;
import com.prankur.eCommerce.repositories.CustomerRepos;
import com.prankur.eCommerce.repositories.SellerRepos;
import com.prankur.eCommerce.utils.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
//@JsonFilter(PropertyFilter)
public class AdminServices
{
    @Autowired
    CustomerRepos customerRepos;

    @Autowired
    SellerRepos sellerRepos;

    @Value("${spring.mail.from.email}")
    private String from;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    public MappingJacksonValue returnAllCustomersFiltered(Integer pageNumber, Integer pageSize, String sortBy, String email)
    {
//        email = "%"+email+"%";
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(new Sort.Order(Sort.Direction.DESC,sortBy)));
        List<Customer> customerList = customerRepos.findByEmailLike(pageable,email);
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("id","email","firstName","middleName","lastName","isActive");
        SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();
        simpleFilterProvider.addFilter("CustomerFilter",simpleBeanPropertyFilter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(customerList);
        mappingJacksonValue.setFilters(simpleFilterProvider);
        System.out.println("Customer Filter " + mappingJacksonValue.getValue());
        return mappingJacksonValue;

    }

    public MappingJacksonValue returnAllSellersFiltered(Integer pageNumber, Integer pagesize, String sortBy, String email)
    {
        Pageable pageable = PageRequest.of(pageNumber,pagesize, Sort.by(new Sort.Order(Sort.Direction.DESC,sortBy)));
        List<Seller> sellerList = sellerRepos.findByEmailLike(pageable,email);
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("id","firstName","middleName","lastName","email","isActive","companyName","addresses","companyContact");
        SimpleFilterProvider filterProvider= new SimpleFilterProvider();
        filterProvider.addFilter("SellerFilter",simpleBeanPropertyFilter);
        MappingJacksonValue mappingJacksonValue= new MappingJacksonValue(sellerList);
        mappingJacksonValue.setFilters(filterProvider);
        System.out.println("Seller Filter " + mappingJacksonValue.getValue());
        return mappingJacksonValue;
    }

    public String activateCustomer(Long id)
    {
        String response = null;
        Optional<Customer> customers = customerRepos.findById(id);
        if (customers.isPresent())
        {
            Customer customer = customers.get();
            if (customer.getIsActive()==true)
            {
                response ="User Account is Already Activated.";
            }
            else
            {
                customer.setIsActive(true);
                customerRepos.save(customer);
                String mailBody = "Hey "+customer.getFirstName()+" "+customer.getEmail()+" your Account has been activated";
                Email email = new Email(from,customer.getEmail(),"Account Activated",mailBody);
                applicationEventPublisher.publishEvent(new OnAccountActivationEvent(email));
                response = "Account has been activated";
            }
        }
        else {
            response = "User Not Found";
            throw new ResourceNotFoundException("User Not Found");
        }

        return  response;
    }

    public String activateSeller(Long id)
    {
        String response = null;
        Optional<Seller> sellers = sellerRepos.findById(id);
        if (sellers.isPresent())
        {
            Seller seller = sellers.get();
            if (seller.getIsActive()==true)
            {
                response ="User Account is Already Activated.";
            }
            else
            {
                seller.setIsActive(true);
                sellerRepos.save(seller);
                String mailBody = "Hey "+seller.getFirstName()+"( "+seller.getEmail()+" ), your Account has been activated";
                Email email = new Email(from,seller.getEmail(),"Account Activated",mailBody);
                applicationEventPublisher.publishEvent(new OnAccountActivationEvent(email));
                response = "Account has been activated";
            }
        }
        else {
            response = "User Not Found";
            throw new ResourceNotFoundException("User Not Found");
        }

        return  response;
    }

    public String deActivateCustomer(Long id)
    {
        String response = null;
        Optional<Customer> customers = customerRepos.findById(id);
        if (customers.isPresent())
        {
            Customer customer = customers.get();
            if (customer.getIsActive()==false)
            {
                response ="User Account is not Active.";
            }
            else
            {
                customer.setIsActive(false);
                customerRepos.save(customer);
                String mailBody = "Hey "+customer.getFirstName()+" "+customer.getEmail()+" your Account has been deactivated";
                Email email = new Email(from,customer.getEmail(),"Account deactivated",mailBody);
                applicationEventPublisher.publishEvent(new OnAccountActivationEvent(email));
                response = "Account has been deactivated";
            }
        }
        else {
            response = "User Not Found";
            throw new ResourceNotFoundException("User Not Found");
        }

        return  response;
    }

    public String deActivateSeller(Long id)
    {
        String response = null;
        Optional<Seller> sellers = sellerRepos.findById(id);
        if (sellers.isPresent())
        {
            Seller seller = sellers.get();
            if (seller.getIsActive()==false)
            {
                response ="User Account is not Active.";
            }
            else
            {
                seller.setIsActive(false);
                sellerRepos.save(seller);
                String mailBody = "Hey "+seller.getFirstName()+"( "+seller.getEmail()+" ), your Account has been activated";
                Email email = new Email(from,seller.getEmail(),"Account deactivated",mailBody);
                applicationEventPublisher.publishEvent(new OnAccountActivationEvent(email));
                response = "Account has been deactivated";
            }
        }
        else {
            response = "User Not Found";
            throw new ResourceNotFoundException("User Not Found");
        }

        return  response;
    }



}
