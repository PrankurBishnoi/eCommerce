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

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Entering Roles and initial users");

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

                Iterator<Address> i = addresses.iterator();
                while(i.hasNext())
                    customer.addAddress(i.next());
            customerRepos.save(customer);
        }
    }
}
