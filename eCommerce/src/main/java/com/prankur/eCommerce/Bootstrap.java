package com.prankur.eCommerce;

import com.prankur.eCommerce.enums.Roles;
import com.prankur.eCommerce.models.Address;
import com.prankur.eCommerce.models.Customer;
import com.prankur.eCommerce.models.GrantAuthorityImpl;
import com.prankur.eCommerce.models.User;
import com.prankur.eCommerce.repositories.CustomerRepos;
import com.prankur.eCommerce.repositories.RolesRepos;
import com.prankur.eCommerce.repositories.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class Bootstrap implements ApplicationRunner {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepos userRepos;

    @Autowired
    CustomerRepos customerRepos;

    @Autowired
    RolesRepos rolesRepos;

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        System.out.println("Entering Roles and initial users");

        rolesRepos.save(new GrantAuthorityImpl(Roles.ADMIN.getRoles()));
        rolesRepos.save(new GrantAuthorityImpl(Roles.CUSTOMER.getRoles()));
        rolesRepos.save(new GrantAuthorityImpl(Roles.SELLER.getRoles()));

        Set<Address> addresses = new HashSet<>();
        Address address = new Address("dcdsc","sdcsd","sdcdsc","dcdsc",1231,"asddc");
        addresses.add(address);
//        User user = new User();
        User user = new User("ABC@gmail.com","ABC","DEF","GHI",passwordEncoder.encode("abc@123"),false,false,addresses, Arrays.asList(new GrantAuthorityImpl(Roles.CUSTOMER.toString())),true,true,true,false,0);
        userRepos.save(user);
    }
}
