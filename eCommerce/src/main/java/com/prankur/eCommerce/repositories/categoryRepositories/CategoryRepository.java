package com.prankur.eCommerce.repositories.categoryRepositories;

import com.prankur.eCommerce.models.category.Category;
import com.prankur.eCommerce.models.users.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category,Long>
{
    Category findByName(String name);
//    Category findById(Long parentId);

    @Query(value = "from Category where parent_id = :id")
    Category findByParentId(Long id);

    @Query(value = "from Category")
    List<Category> findAllCategories(Pageable pageable);

    @Query(value = "from Category where name Like %:query%")
    List<Category> findAllCategories(Pageable pageable, String query);

}
