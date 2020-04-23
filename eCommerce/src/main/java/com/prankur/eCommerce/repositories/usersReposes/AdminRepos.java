package com.prankur.eCommerce.repositories.usersReposes;

import com.prankur.eCommerce.models.users.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepos extends CrudRepository<Admin, Long>
{

}
