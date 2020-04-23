package com.prankur.eCommerce;

import com.prankur.eCommerce.enums.Role;
import com.prankur.eCommerce.models.*;
import com.prankur.eCommerce.models.users.Admin;
import com.prankur.eCommerce.models.users.Customer;
import com.prankur.eCommerce.models.users.User;
import com.prankur.eCommerce.repositories.usersRepositories.AdminRepository;
import com.prankur.eCommerce.repositories.usersRepositories.CustomerRepository;
import com.prankur.eCommerce.repositories.RolesRepository;
import com.prankur.eCommerce.repositories.usersRepositories.UserRepository;
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
    UserRepository userRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    AdminRepository adminRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Entering Role and initial users");

        Roles roles = null;
        roles = rolesRepository.findByAuthority(Role.ADMIN.getRoles());
        if (roles ==null)
        {
            rolesRepository.save(new Roles(Role.ADMIN.getRoles()));
            rolesRepository.save(new Roles(Role.CUSTOMER.getRoles()));
            rolesRepository.save(new Roles(Role.SELLER.getRoles()));
        }


        User user = null;
        user = userRepository.findByEmail("prankur4091@gmail.com");
        if (user == null)
        {
            Set<Address> addressess = new HashSet<>();
            Address addreess = new Address("a", "aa", "aaa", "aaaa", 1231, "aaaaa");
            addressess.add(addreess);
            Admin admin = new Admin("prankur4091@gmail.com", "This", "is", "Admin",
                    passwordEncoder.encode("Admin@4091"), false, true, addressess,
                    Arrays.asList(rolesRepository.findByAuthority(Role.ADMIN.getRoles())), false, false, false, true, 0);
            Iterator<Address> i = addressess.iterator();
            while (i.hasNext())
                admin.addAddress(i.next());
            adminRepository.save(admin);
            System.out.println("Admin details added");
        }
        else
            System.out.println("Admin details already present");

//        if (false) {
//            rolesRepository.save(new Roles("ROLE_" + Role.CUSTOMER.toString()));
//            rolesRepository.save(new Roles("ROLE_" + Role.SELLER.toString()));
//            rolesRepository.save(new Roles("ROLE_" + Role.ADMIN.toString()));
//
//            Set<Address> addresses = new HashSet<>();
//            Address address = new Address("dcdsc", "sdcsd", "sdcdsc", "dcdsc", 1231, "asddc");
//            addresses.add(address);
//            Customer customer = new Customer("ABC@gmail.com", "ABC", "DEF", "GHI",
//                    passwordEncoder.encode("abc@123"),
//                    false, false, addresses,
//                    Arrays.asList(new Roles("ROLE_" + Role.CUSTOMER.toString())),
//                    true, true, true, false, 0, 1234567890L);
//
//                Iterator<Address> j = addresses.iterator();
//
//                while(j.hasNext())
//                    customer.addAddress(j.next());
//            customerRepository.save(customer);
//        }
    }
}
