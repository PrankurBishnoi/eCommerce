package com.prankur.eCommerce.repositories;

import com.prankur.eCommerce.models.GrantAuthorityImpl;
import org.springframework.data.repository.CrudRepository;

public interface RolesRepos extends CrudRepository<GrantAuthorityImpl,Long> {
}
