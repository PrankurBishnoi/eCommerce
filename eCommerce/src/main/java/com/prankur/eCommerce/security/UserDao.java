package com.prankur.eCommerce.security;

import com.prankur.eCommerce.models.users.User;
import com.prankur.eCommerce.repositories.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    @Autowired
    UserRepos userRepos;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//    List<User> userList = Arrays.asList(
//            new User("user", passwordEncoder.encode("pass"), Arrays.asList(new GrantAuthorityImpl("ROLE_USER"))),
//            new User("admin", passwordEncoder.encode("pass"), Arrays.asList(new GrantAuthorityImpl("ROLE_ADMIN"))));

    AppUser loadUserByUsername(String username)
    {
        User user = userRepos.findByEmail(username);
        System.out.println("122"+user.getUsername()+user.getPassword());
        System.out.println(user.getPassword());
//        Optional<User> userOptional = userList.stream().filter(e -> e.getUsername().equals(username)).findFirst();
        if(user!=null)
        {
            AppUser appUser = new AppUser(user);
            System.out.println("123");
            return appUser;
        }
        else
        {
            throw new RuntimeException("User not found");
        }
    }

}
