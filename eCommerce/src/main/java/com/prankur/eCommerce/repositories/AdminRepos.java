package com.prankur.eCommerce.repositories;

import com.prankur.eCommerce.models.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepos extends CrudRepository<Admin, Long>
{

}