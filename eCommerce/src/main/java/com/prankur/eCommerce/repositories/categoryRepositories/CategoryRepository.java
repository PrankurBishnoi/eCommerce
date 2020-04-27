package com.prankur.eCommerce.repositories.categoryRepositories;

import com.prankur.eCommerce.models.category.Category;
import com.prankur.eCommerce.models.users.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category,Long>
{
    Category findByName(String name);
//    Category findById(Long parentId);

    @Query(value = "select * from category where parent_id = :id",nativeQuery = true)
    Category findByParentId(Long id);

}
