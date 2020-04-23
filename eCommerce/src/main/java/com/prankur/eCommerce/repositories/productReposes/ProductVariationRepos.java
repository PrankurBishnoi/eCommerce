package com.prankur.eCommerce.repositories.productReposes;

import com.prankur.eCommerce.models.product.ProductVariation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariationRepos extends CrudRepository<ProductVariation,Long>
{

}
