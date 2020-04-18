package com.prankur.eCommerce;

import com.prankur.eCommerce.enums.Roles;
import com.prankur.eCommerce.models.*;
import com.prankur.eCommerce.repositories.AdminRepos;
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
import java.util.Iterator;
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

    @Autowired
    AdminRepos adminRepos;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Entering Roles and initial users");

        Set<Address> addressess = new HashSet<>();
        Address addreess  = new Address("a", "aa", "aaa", "aaaa", 1231, "aaaaa");
        addressess.add(addreess);
        Admin admin = new Admin("prankur4091@gmail.com","This","is","Admin",
                                passwordEncoder.encode("Admin@4091"),false,true,addressess,
                                Arrays.asList(new GrantAuthorityImpl(Roles.ADMIN.getRoles())),false,false,false,true,0);
        Iterator<Address> i = addressess.iterator();
        while(i.hasNext())
            admin.addAddress(i.next());
        adminRepos.save(admin);
        System.out.println("Admin details added");


        if (false) {
            rolesRepos.save(new GrantAuthorityImpl("ROLE_" + Roles.CUSTOMER.toString()));
            rolesRepos.save(new GrantAuthorityImpl("ROLE_" + Roles.SELLER.toString()));
            rolesRepos.save(new GrantAuthorityImpl("ROLE_" + Roles.ADMIN.toString()));

            Set<Address> addresses = new HashSet<>();
            Address address = new Address("dcdsc", "sdcsd", "sdcdsc", "dcdsc", 1231, "asddc");
            addresses.add(address);
            Customer customer = new Customer("ABC@gmail.com", "ABC", "DEF", "GHI",
                    passwordEncoder.encode("abc@123"),
                    false, false, addresses,
                    Arrays.asList(new GrantAuthorityImpl("ROLE_" + Roles.CUSTOMER.toString())),
                    true, true, true, false, 0, 1234567890L);

                Iterator<Address> j = addresses.iterator();

                while(i.hasNext())
                    customer.addAddress(j.next());
            customerRepos.save(customer);
        }
    }
}
