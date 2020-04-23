package com.prankur.eCommerce.repositories.categoryReposes;

import com.prankur.eCommerce.models.category.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category,Long>
{


}
