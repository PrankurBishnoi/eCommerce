package com.prankur.eCommerce.repositories;

import com.prankur.eCommerce.models.users.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CustomerRepos extends CrudRepository<Customer,Long>
{
    List<Customer> findAll();
    List<Customer> findByEmailLike(Pageable pageable, String Email);
    Customer findByEmail(String email);
}
