package com.prankur.eCommerce.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.Map;

@Converter
public class MetaDataConverter implements AttributeConverter<Map<String, String>, String>
{
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, String> attribute)
    {
        String metaDataProductInformation = null;
        try {
            metaDataProductInformation = objectMapper.writeValueAsString(attribute);
            System.out.println(metaDataProductInformation);
        } catch (IOException e) {
            System.out.println(e);
//            e.printStackTrace();
        }
        return metaDataProductInformation;
    }

    @Override
    public Map<String, String> convertToEntityAttribute(String dbData)
    {
        Map<String,String> productVariationInfo = null;
        try {
            productVariationInfo = objectMapper.readValue(dbData,Map.class);
        } catch (IOException e) {
            System.out.println(e);
//            e.printStackTrace();
        }

        return productVariationInfo;
    }
}
