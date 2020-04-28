package com.prankur.eCommerce.controllers;

import com.prankur.eCommerce.cos.MetadataFieldCO;
import com.prankur.eCommerce.cos.MetadataToCategoryCO;
import com.prankur.eCommerce.dtos.Response;
import com.prankur.eCommerce.dtos.ViewCategoryDTO;
import com.prankur.eCommerce.models.category.CategoryMetadataField;
import com.prankur.eCommerce.repositories.categoryRepositories.MetadataFieldRepository;
import com.prankur.eCommerce.repositories.categoryRepositories.CategoryRepository;
import com.prankur.eCommerce.repositories.categoryRepositories.MetadataFieldValuesRepository;
import com.prankur.eCommerce.services.categoryServices.CategoryService;
import com.prankur.eCommerce.services.categoryServices.MetadataFieldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@RestController
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

    Logger logger = LoggerFactory.getLogger(CategoryController.class);


    @PostMapping("/admin/addMetadataField")
    public ResponseEntity<Response> addMetadataField(@RequestBody MetadataFieldCO metadataFieldCO)
    {
        Response response = new Response();
        CategoryMetadataField categoryMetadataFieldUnique = metadataFieldRepository.findByName(metadataFieldCO.getName());
//        System.out.println(4 + categoryMetadataFieldUnique.getName());
        if (categoryMetadataFieldUnique == null)
        {
            response = metadataFieldService.addMetadataField(metadataFieldCO);
        }
        else
        {
            response.setResponseMessage("Metadata Field already exists in Database");
        }
        ResponseEntity entity = ResponseEntity.status(HttpStatus.OK).body(response);
        return entity;
    }

    @GetMapping("/admin/metadataFields")
    List<CategoryMetadataField> getMetadataFields(@RequestParam(defaultValue = "10") String page, @RequestParam(defaultValue = "0") String pageoff, @RequestParam(defaultValue = "id") String sortby, @RequestParam(defaultValue = "ASC") String order, @RequestParam(defaultValue = "*") String query)
    {
        Integer pagesize = Integer.parseInt(page);
        Integer pageoffset = Integer.parseInt(pageoff);
        List<CategoryMetadataField> categoryMetadataFields = categoryService.returnMetadataFields(pageoffset,pagesize,sortby,order,query);
        return categoryMetadataFields;
    }

    @PostMapping("/admin/addCategory")
    ResponseEntity<Response> addCategory(@RequestParam String name, @RequestParam(defaultValue = "0") String parentsId)
    {
        Response response = new Response();
//        System.out.println("name " + name);
        Long parentId = Long.parseLong(parentsId);
//        System.out.println("id " + parentId);
        ResponseEntity<Response> responseEntity = null;
        response.setResponseMessage(categoryService.addCategory(name, parentId));
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(response);
        return responseEntity;
    }

    @GetMapping("/admin/viewOneCategory/{id}")
    ViewCategoryDTO viewCategory(@PathVariable Long id)
    {
        //        ResponseEntity<Category> responseEntity = null;
//        System.out.println("id" + id);
        logger.info("id: "+id);
        ViewCategoryDTO viewCategoryDTO ;
        viewCategoryDTO = categoryService.getOneCategory(id);
        logger.info("3: " + viewCategoryDTO.getCategory().getName());
        return viewCategoryDTO;
    }

    @GetMapping("/admin/viewAllCategories")
    List<ViewCategoryDTO> viewAllCategoriesAdmin(@RequestParam(defaultValue = "10") String page, @RequestParam(defaultValue = "0") String pageoff, @RequestParam(defaultValue = "id") String sortby, @RequestParam(defaultValue = "ASC") String order, @RequestParam(defaultValue = "*") String query)
    {
        List<ViewCategoryDTO> viewCategoryDTOS;
        Integer pagesize = Integer.parseInt(page);
        Integer pageOffSet = Integer.parseInt(pageoff);
        viewCategoryDTOS = categoryService.viewAllCategoriesForAdmin(pageOffSet,pagesize,sortby,order,query);
        return viewCategoryDTOS;
    }

    @PutMapping("/admin/updateCategory")
    Response updateCategory(@RequestParam(defaultValue = "0") String id, @RequestParam String newName)
    {
        Response response = new Response();
        Long iid = Long.parseLong(id);
        logger.info("id = "+id);
        response.setResponseMessage(categoryService.updateCategory(iid,newName));
        return response;
    }

    @PostMapping("/admin/addMetaValueToCategory")
    Response addMetaValueToCategory(@RequestBody MetadataToCategoryCO metadataToCategoryCO)
    {
        HashMap<String, HashSet<String>> hashMap = metadataToCategoryCO.getFieldValues();
        logger.trace(metadataToCategoryCO.toString());
        Response response = new Response();
        response.setResponseMessage(categoryService.addMetaValueToCategory(metadataToCategoryCO));
        return response;
    }

    @PutMapping("/admin/updateMetadataField")
    Response updateMetadataField(@RequestBody MetadataToCategoryCO metadataToCategoryCO)
    {
        Response response = new Response();
        response.setResponseMessage(categoryService.updateMetadataFieldValues(metadataToCategoryCO));
        return response;
    }

    


}
