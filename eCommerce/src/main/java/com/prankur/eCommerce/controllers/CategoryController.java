package com.prankur.eCommerce.controllers;

import com.prankur.eCommerce.cos.MetadataFieldCO;
import com.prankur.eCommerce.models.category.Category;
import com.prankur.eCommerce.models.category.CategoryMetadataField;
import com.prankur.eCommerce.repositories.categoryRepositories.MetadataFieldRepository;
import com.prankur.eCommerce.repositories.categoryRepositories.CategoryRepository;
import com.prankur.eCommerce.repositories.categoryRepositories.MetadataFieldValuesRepository;
import com.prankur.eCommerce.services.categoryServices.CategoryService;
import com.prankur.eCommerce.services.categoryServices.MetadataFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
//@RequestMapping("category")
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

    @GetMapping("/metadataFields")
    List<CategoryMetadataField> getMetadataFields()
    {
        List<CategoryMetadataField> categoryMetadataFields = categoryService.returnMetadataFields();
        return categoryMetadataFields;
    }

    @PostMapping("/addCategory")
    ResponseEntity<String> addCategory(@RequestParam String name, @RequestParam(defaultValue = "0") String parentsId)
    {
        String response = null;
        System.out.println("name " + name);
        Long parentId = Long.parseLong(parentsId);
        System.out.println("id " + parentId);
        ResponseEntity<String> responseEntity = null;
        response = categoryService.addCategory(name, parentId);
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(response);
        return responseEntity;
    }




}
