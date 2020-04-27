package com.prankur.eCommerce.repositories.categoryRepositories;

import com.prankur.eCommerce.models.category.CategoryMetadataField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetadataFieldRepository extends CrudRepository<CategoryMetadataField,Long>
{
    CategoryMetadataField findByName(String name);

//    @Query("select * from CategoryMetadataField where name like %:query%")
//    List<CategoryMetadataField> findWithQuery(Pageable pageable, String query);

//    @Query("select * from CategoryMetadataField")
    List<CategoryMetadataField> findAll(Pageable pageable);



}
