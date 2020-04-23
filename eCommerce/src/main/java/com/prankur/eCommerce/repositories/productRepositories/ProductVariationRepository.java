package com.prankur.eCommerce.repositories.productRepositories;

import com.prankur.eCommerce.models.product.ProductVariation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariationRepository extends CrudRepository<ProductVariation,Long>
{

}
