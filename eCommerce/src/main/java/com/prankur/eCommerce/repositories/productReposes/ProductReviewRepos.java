package com.prankur.eCommerce.repositories.productReposes;

import com.prankur.eCommerce.models.product.ProductReview;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReviewRepos extends CrudRepository<ProductReview,Long>
{

}
