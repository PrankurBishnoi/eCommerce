package com.prankur.eCommerce.repositories;

import com.prankur.eCommerce.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepos extends CrudRepository<User,Long>
{
    User findByEmail(String email);
    Boolean existsByEmail(String email);
}
