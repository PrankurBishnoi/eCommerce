package com.prankur.eCommerce.repositories;

import com.prankur.eCommerce.models.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CustomerRepos extends CrudRepository<Customer,Long>
{
}
