package com.prankur.eCommerce.repositories.productRepositories;

import com.prankur.eCommerce.models.product.ProductReview;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReviewRepository extends CrudRepository<ProductReview,Long>
{

}
