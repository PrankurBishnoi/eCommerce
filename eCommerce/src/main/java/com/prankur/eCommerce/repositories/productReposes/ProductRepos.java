package com.prankur.eCommerce.repositories.productReposes;

import com.prankur.eCommerce.models.product.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepos extends CrudRepository<Product,Long>
{

}
