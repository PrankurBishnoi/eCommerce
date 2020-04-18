package com.prankur.eCommerce.repositories;

import com.prankur.eCommerce.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepos extends CrudRepository<User,Long>
{
    User findByEmail(String email);
    Boolean existsByEmail(String email);
    List<User> findAll();
}
