package com.prankur.eCommerce.repositories.productRepositories;

import com.prankur.eCommerce.models.product.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product,Long>
{

}
