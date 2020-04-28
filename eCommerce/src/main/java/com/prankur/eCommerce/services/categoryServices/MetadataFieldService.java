package com.prankur.eCommerce.services.categoryServices;

import com.prankur.eCommerce.cos.MetadataFieldCO;
import com.prankur.eCommerce.dtos.Response;
import com.prankur.eCommerce.models.category.CategoryMetadataField;
import com.prankur.eCommerce.repositories.categoryRepositories.MetadataFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetadataFieldService
{
    @Autowired
    MetadataFieldRepository metadataFieldRepository;

    public Response addMetadataField(MetadataFieldCO metadataFieldCO)
    {
        Response response = new Response();
        CategoryMetadataField categoryMetadataField = new CategoryMetadataField(metadataFieldCO.getName());
        metadataFieldRepository.save(categoryMetadataField);
        CategoryMetadataField checkMetaField ;
        checkMetaField = metadataFieldRepository.findByName(metadataFieldCO.getName());
        if(checkMetaField == null)
            response.setResponseMessage("Couldn't save to database");
        else
            response.setResponseMessage("Metadata Field addedd to the database");
        return response;
    }



}
