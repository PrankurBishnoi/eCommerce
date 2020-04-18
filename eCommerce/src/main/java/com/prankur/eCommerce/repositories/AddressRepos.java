package com.prankur.eCommerce.repositories;

import com.prankur.eCommerce.models.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface AddressRepos extends CrudRepository<Address,Long>
{
    @Query(value = "select * from address where user_id = :userId",nativeQuery = true)
    Optional<Address> findByUserId(Long userId);
}
