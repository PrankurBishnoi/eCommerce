package com.prankur.eCommerce.services.categoryServices;

import com.prankur.eCommerce.cos.MetadataToCategoryCO;
import com.prankur.eCommerce.dtos.ViewCategoryDTO;
import com.prankur.eCommerce.exceptions.customExceptions.ResourceAlreadyExistException;
import com.prankur.eCommerce.exceptions.customExceptions.ResourceNotFoundException;
import com.prankur.eCommerce.models.category.Category;
import com.prankur.eCommerce.models.category.CategoryMetadataField;
import com.prankur.eCommerce.models.category.MetadataFieldValues;
import com.prankur.eCommerce.models.category.MetadataFieldValuesIdCompositeKey;
import com.prankur.eCommerce.repositories.categoryRepositories.CategoryRepository;
import com.prankur.eCommerce.repositories.categoryRepositories.MetadataFieldRepository;
import com.prankur.eCommerce.repositories.categoryRepositories.MetadataFieldValuesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService
{
    @Autowired
    MetadataFieldRepository metadataFieldRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    MetadataFieldValuesRepository metadataFieldValuesRepository;

    Logger logger = LoggerFactory.getLogger(CategoryService.class);


    public List<CategoryMetadataField> returnMetadataFields(Integer pageOffset, Integer pageSize, String sortBy, String order, String query)
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
        if (query.equals("*"))
            categoryMetadataFields = metadataFieldRepository.findAllMetadatas(pageable);
        else
            categoryMetadataFields = metadataFieldRepository.findAllMetadatas(pageable,query);
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

    public List<ViewCategoryDTO> viewAllCategoriesForAdmin(Integer pageOffset, Integer pageSize, String sortBy, String order, String query)
    {
        List<ViewCategoryDTO>viewCategoryDTOList = new ArrayList<>();
        List<Category> categories;
        Pageable pageable;
        if (order.equals("DESC"))
            pageable = PageRequest.of(pageOffset,pageSize,Sort.by(new Sort.Order(Sort.Direction.DESC,sortBy)));
        else
            pageable = PageRequest.of(pageOffset,pageSize,Sort.by(new Sort.Order(Sort.Direction.ASC,sortBy)));
        if (query.equals("*"))
            categories = categoryRepository.findAllCategories(pageable);
        else
            categories = categoryRepository.findAllCategories(pageable,query);
        for (Category c: categories)
        {
            ViewCategoryDTO temp = getOneCategory(c.getId());
            viewCategoryDTOList.add(temp);
        }
        return viewCategoryDTOList;
    }

    public String updateCategory(Long id, String newName)
    {
        String response = null;
        if(!categoryRepository.findById(id).isPresent())
        {
            throw new ResourceNotFoundException("Invalid id, Category with id: " + id + " not found");
        }
        Category category = categoryRepository.findById(id).get();
        Category temp = null;
        temp = categoryRepository.findByName(newName);
        if (temp != null)
        {
            throw new ResourceAlreadyExistException("Category name already exists");
        }
        else
        {
            category.setName(newName);
            categoryRepository.save(category);
            response = "Category Successfully updated";
        }
        return response;
    }


    public String addMetaValueToCategory(MetadataToCategoryCO metadataToCategoryCO)
    {
        String response = null;
        Optional<Category>optionalCategory = categoryRepository.findById(Long.parseLong(metadataToCategoryCO.getCategoryId()));
        if (!optionalCategory.isPresent())
            throw new ResourceNotFoundException("Category with id " + metadataToCategoryCO.getCategoryId() + " not found in database.");
        HashMap<String, HashSet<String>> fieldValues = metadataToCategoryCO.getFieldValues();
        Set<String> fields = fieldValues.keySet();
        fields.forEach(fieldId -> {
            Optional<CategoryMetadataField> temp = metadataFieldRepository.findById(Long.parseLong(fieldId));
            if (!temp.isPresent())
                throw new ResourceNotFoundException("Some invalid field id: " + fieldId + " doesn't have value.");
        });

        fields.forEach(fieldId -> {
            if (fieldValues.get(fieldId).isEmpty())
                throw new ResourceNotFoundException("None of the Field have any value");
        });

        for (String fieldIds : fields) {
            Long fieldId = Long.parseLong(fieldIds);
            MetadataFieldValues metadataFieldValues = new MetadataFieldValues();
            Optional<CategoryMetadataField> categoryMetadataFields = metadataFieldRepository.findById(fieldId);
            CategoryMetadataField categoryMetadataField = categoryMetadataFields.get();
            metadataFieldValues.setId(new MetadataFieldValuesIdCompositeKey());
            metadataFieldValues.setCategoryMetadataField(categoryMetadataFields.get());
            metadataFieldValues.setCategory(optionalCategory.get());
            Set<String> fieldValuesSet = fieldValues.get(fieldIds);
            String csvs = String.join(",", fieldValuesSet);
            metadataFieldValues.setValue(csvs);
            logger.trace("*****************");
            metadataFieldValuesRepository.save(metadataFieldValues);
        }
        response = "Fields and their values added Successfully";
        return response;
    }
}
