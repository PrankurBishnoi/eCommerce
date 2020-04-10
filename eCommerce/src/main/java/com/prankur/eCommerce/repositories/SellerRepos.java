package com.prankur.eCommerce.repositories;

import com.prankur.eCommerce.models.Seller;
import org.springframework.data.repository.CrudRepository;

public interface SellerRepos extends CrudRepository<Seller,Long> {
}
