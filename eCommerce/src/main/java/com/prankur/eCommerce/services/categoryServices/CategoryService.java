package com.prankur.eCommerce.services.categoryServices;

import com.prankur.eCommerce.models.category.Category;
import com.prankur.eCommerce.models.category.CategoryMetadataField;
import com.prankur.eCommerce.repositories.categoryRepositories.CategoryRepository;
import com.prankur.eCommerce.repositories.categoryRepositories.MetadataFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService
{
    @Autowired
    MetadataFieldRepository metadataFieldRepository;

    @Autowired
    CategoryRepository categoryRepository;


    public List<CategoryMetadataField> returnMetadataFields()
    {
        Iterable<CategoryMetadataField> categoryMetadataFields = metadataFieldRepository.findAll();
        List<CategoryMetadataField> categoryMetadataFields1 = new ArrayList<>();
        categoryMetadataFields.forEach(categoryMetadataFields1::add);
        return categoryMetadataFields1;
    }

    public String addCategory(String name, Long parentId)
    {
        Category category = null;
        category = categoryRepository.findByName(name);
        if (category != null)
        {
            System.out.println(category);
            return  "Category already Exists";
        }
        else if (parentId == 0)
        {
            Category category1 = new Category(name);
            categoryRepository.save(category1);
            return  "Category added to database";
        }
        else
        {
            Optional<Category> categories = categoryRepository.findById(parentId);
            Category category1 = new Category(name,categories.get());
            categoryRepository.save(category1);
            return  "Category added to database";
        }
    }

}
