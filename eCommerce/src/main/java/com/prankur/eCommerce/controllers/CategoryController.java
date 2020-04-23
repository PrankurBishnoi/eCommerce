package com.prankur.eCommerce.controllers;

import com.prankur.eCommerce.cos.MetadataFieldCO;
import com.prankur.eCommerce.models.category.CategoryMetadataField;
import com.prankur.eCommerce.repositories.categoryRepositories.MetadataFieldRepository;
import com.prankur.eCommerce.repositories.categoryRepositories.CategoryRepository;
import com.prankur.eCommerce.repositories.categoryRepositories.MetadataFieldValuesRepository;
import com.prankur.eCommerce.services.categoryServices.CategoryService;
import com.prankur.eCommerce.services.categoryServices.MetadataFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("category")
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
    public ResponseEntity<String> addMetadataField(@RequestBody MetadataFieldCO metadataFieldCO)
    {
        String response ;
        CategoryMetadataField categoryMetadataFieldUnique = metadataFieldRepository.findByName(metadataFieldCO.getName());
//        System.out.println(4 + categoryMetadataFieldUnique.getName());
        if (categoryMetadataFieldUnique == null)
        {
            response = metadataFieldService.addMetadataField(metadataFieldCO);
        }
        else
        {
            response = "Metadata Field already exists in Database";
        }
        ResponseEntity entity = ResponseEntity.status(HttpStatus.OK).body(response);
        return entity;
    }




}
