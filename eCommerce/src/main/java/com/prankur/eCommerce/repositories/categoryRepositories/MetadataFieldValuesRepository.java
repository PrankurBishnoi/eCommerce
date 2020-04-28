package com.prankur.eCommerce.repositories.categoryRepositories;

import com.prankur.eCommerce.models.category.MetadataFieldValues;
import com.prankur.eCommerce.models.category.MetadataFieldValuesIdCompositeKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MetadataFieldValuesRepository extends CrudRepository<MetadataFieldValues, MetadataFieldValuesIdCompositeKey>
{
    @Query(value = "select * from metadata_field_values where category_id = :category and category_metadata_field_id = :metadataField", nativeQuery = true)
//    @Query("from MetadataFieldValues where categoryId= :category and metadataFieldId= :metadataField")
    Optional<MetadataFieldValues> findByCategoryAndMetadataField(Long category, Long metadataField);



}
