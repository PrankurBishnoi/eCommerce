package com.prankur.eCommerce.services.categoryServices;

import com.prankur.eCommerce.dtos.ViewCategoryDTO;
import com.prankur.eCommerce.exceptions.customExceptions.ResourceNotFoundException;
import com.prankur.eCommerce.models.category.Category;
import com.prankur.eCommerce.models.category.CategoryMetadataField;
import com.prankur.eCommerce.repositories.categoryRepositories.CategoryRepository;
import com.prankur.eCommerce.repositories.categoryRepositories.MetadataFieldRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    Logger logger = LoggerFactory.getLogger(CategoryService.class);


    public List<CategoryMetadataField> returnMetadataFields(Integer pageOffset, Integer pageSize, String sortBy, String order/*, String query*/)
    {
//        Iterable<CategoryMetadataField> categoryMetadataFields = metadataFieldRepository.findAll();
//        List<CategoryMetadataField> categoryMetadataFields1 = new ArrayList<>();
//        categoryMetadataFields.forEach(categoryMetadataFields1::add);
//        return categoryMetadataFields1;
        List<CategoryMetadataField> categoryMetadataFields = null;
        Pageable pageable;
        if (order.equals("DESC"))
            pageable = PageRequest.of(pageOffset, pageSize, Sort.by(new Sort.Order(Sort.Direction.DESC, sortBy)));
        else
            pageable = PageRequest.of(pageOffset, pageSize, Sort.by(new Sort.Order(Sort.Direction.ASC, sortBy)));
//        if (query.equals("*"))
            categoryMetadataFields = metadataFieldRepository.findAll(pageable);
//        else
//            categoryMetadataFields = metadataFieldRepository.findWithQuery(pageable,query);
        return categoryMetadataFields;
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

    public ViewCategoryDTO getOneCategory(Long id)
    {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        List<Category> parentCategories = new ArrayList<>();
        if (optionalCategory.isPresent())
        {
            Category category = optionalCategory.get();
            Category parentCategory = category;
            while(parentCategory.getParentCategory()!=null)
            {
                Optional<Category> parentCategory1 = categoryRepository.findById(parentCategory.getParentCategory().getId());
                if (parentCategory1.isPresent())
                {
                    parentCategory = parentCategory1.get();
                    parentCategories.add(parentCategory);
                }
                else
                {
                    break;
                }
            }
            Category childCategory = null;
            childCategory = categoryRepository.findByParentId(category.getId());
            ViewCategoryDTO viewCategoryDTO = new ViewCategoryDTO();
            viewCategoryDTO.setCategory(category);
            viewCategoryDTO.setChildCategory(childCategory);
            viewCategoryDTO.setParentCategory(parentCategories);
            return viewCategoryDTO;
        }
        else
        {
            throw new ResourceNotFoundException("Category not found");
        }
    }

}
