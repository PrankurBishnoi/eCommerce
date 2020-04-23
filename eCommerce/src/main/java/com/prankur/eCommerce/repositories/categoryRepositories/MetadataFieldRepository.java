package com.prankur.eCommerce.repositories.categoryRepositories;

import com.prankur.eCommerce.models.category.CategoryMetadataField;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetadataFieldRepository extends CrudRepository<CategoryMetadataField,Long>
{
    CategoryMetadataField findByName(String name);
}
