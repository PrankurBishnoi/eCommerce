package com.prankur.eCommerce.cos;

import java.util.HashMap;
import java.util.HashSet;

public class MetadataToCategoryCO
{
    private String categoryId;
    private HashMap<String, HashSet<String>> fieldValues;

    public MetadataToCategoryCO(String categoryId, HashMap<String, HashSet<String>> fieldValues) {
        this.categoryId = categoryId;
        this.fieldValues = fieldValues;
    }

    public MetadataToCategoryCO(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public HashMap<String, HashSet<String>> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(HashMap<String, HashSet<String>> fieldValues) {
        this.fieldValues = fieldValues;
    }

    @Override
    public String toString() {
        return "MetadataToCategoryCO{" +
                "categoryId='" + categoryId + '\'' +
                ", fieldValues=" + fieldValues +
                '}';
    }

    public MetadataToCategoryCO() {
    }
}
