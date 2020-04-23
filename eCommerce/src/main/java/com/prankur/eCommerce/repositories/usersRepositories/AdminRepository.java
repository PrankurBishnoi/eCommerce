package com.prankur.eCommerce.repositories.usersRepositories;

import com.prankur.eCommerce.models.users.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Long>
{

}
