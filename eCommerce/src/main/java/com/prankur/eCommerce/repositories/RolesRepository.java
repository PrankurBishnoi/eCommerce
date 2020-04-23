package com.prankur.eCommerce.repositories;

import com.prankur.eCommerce.models.Roles;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends CrudRepository<Roles,Long>
{
    Roles findByAuthority(String authority);
}
