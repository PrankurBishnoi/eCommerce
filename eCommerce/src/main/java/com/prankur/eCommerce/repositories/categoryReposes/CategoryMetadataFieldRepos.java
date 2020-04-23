package com.prankur.eCommerce.repositories.categoryReposes;

import com.prankur.eCommerce.models.category.CategoryMetadataField;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryMetadataFieldRepos extends CrudRepository<CategoryMetadataField,Long>
{

}