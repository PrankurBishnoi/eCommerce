package com.prankur.eCommerce.repositories.categoryReposes;

import com.prankur.eCommerce.models.category.MetadataFieldValues;
import com.prankur.eCommerce.models.category.MetadataFieldValuesIdCompositeKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetadataFieldValuesRepos extends CrudRepository<MetadataFieldValues, MetadataFieldValuesIdCompositeKey>
{

}
