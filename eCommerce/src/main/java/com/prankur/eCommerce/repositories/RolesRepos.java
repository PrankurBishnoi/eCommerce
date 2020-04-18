package com.prankur.eCommerce.repositories;

import com.prankur.eCommerce.models.GrantAuthorityImpl;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepos extends CrudRepository<GrantAuthorityImpl,Long> {
}
