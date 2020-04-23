package com.prankur.eCommerce.controllers;

import com.prankur.eCommerce.cos.MetadataFieldCO;
import com.prankur.eCommerce.models.category.CategoryMetadataField;
import com.prankur.eCommerce.repositories.categoryRepositories.MetadataFieldRepository;
import com.prankur.eCommerce.repositories.categoryRepositories.CategoryRepository;
import com.prankur.eCommerce.repositories.categoryRepositories.MetadataFieldValuesRepository;
import com.prankur.eCommerce.services.categoryServices.CategoryService;
import com.prankur.eCommerce.services.categoryServices.MetadataFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CategoryController
{
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    MetadataFieldRepository metadataFieldRepository;

    @Autowired
    MetadataFieldValuesRepository metadataFieldValuesRepository;

    @Autowired
    MetadataFieldService metadataFieldService;

    @Autowired
    CategoryService categoryService;


    @PostMapping("/addMetadataField")
    String addMetadataField(@RequestBody MetadataFieldCO metadataFieldCO)
    {
        CategoryMetadataField categoryMetadataFieldUnique = metadataFieldRepository.findByName(metadataFieldCO.getName());
        if (categoryMetadataFieldUnique == null)
        {
            String response = metadataFieldService.addMetadataField(metadataFieldCO);
            return response;

        }
        else
            return "Metadata Field already exists in Database";
    }




}
