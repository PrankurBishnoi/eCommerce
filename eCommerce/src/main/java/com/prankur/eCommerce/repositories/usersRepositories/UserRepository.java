package com.prankur.eCommerce.repositories.usersRepositories;

import com.prankur.eCommerce.models.users.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User,Long>
{
    User findByEmail(String email);
    Boolean existsByEmail(String email);
    List<User> findAll();
}
