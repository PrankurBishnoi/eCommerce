package com.prankur.eCommerce.services.categoryServices;

import com.prankur.eCommerce.cos.MetadataFieldCO;
import com.prankur.eCommerce.models.category.CategoryMetadataField;
import com.prankur.eCommerce.repositories.categoryRepositories.MetadataFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetadataFieldService
{
    @Autowired
    MetadataFieldRepository metadataFieldRepository;

    public String addMetadataField(MetadataFieldCO metadataFieldCO)
    {
        CategoryMetadataField categoryMetadataField = new CategoryMetadataField(metadataFieldCO.getName());
        metadataFieldRepository.save(categoryMetadataField);
        CategoryMetadataField checkMetaField = null;
        checkMetaField = metadataFieldRepository.findByName(metadataFieldCO.getName());
        if(checkMetaField == null)
            return "Couldn't save to database";
        else
            return "Metadata Field addedd to the database";

    }



}
