package com.prankur.eCommerce.repositories;

import com.prankur.eCommerce.models.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface AddressRepos extends CrudRepository<Address,Long>
{
    @Query(value = "select * from address where user_id = :userId",nativeQuery = true)
    Optional<Address> findByUserId(Long userId);
}
