package com.prankur.eCommerce.cos;

import com.prankur.eCommerce.models.category.MetadataFieldValues;

import java.util.Set;

public class ViewCategoriesSellerCO
{
    private Long categoryId;

    private String categoryName;

    private Set<MetadataFieldValues> metadataFieldValues;

    private ViewCategoriesSellerCO parent;

    public ViewCategoriesSellerCO(Long categoryId, String categoryName, Set<MetadataFieldValues> metadataFieldValues, ViewCategoriesSellerCO parent) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.metadataFieldValues = metadataFieldValues;
        this.parent = parent;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Set<MetadataFieldValues> getMetadataFieldValues() {
        return metadataFieldValues;
    }

    public void setMetadataFieldValues(Set<MetadataFieldValues> metadataFieldValues) {
        this.metadataFieldValues = metadataFieldValues;
    }

    public ViewCategoriesSellerCO getParent() {
        return parent;
    }

    public void setParent(ViewCategoriesSellerCO parent) {
        this.parent = parent;
    }

    public ViewCategoriesSellerCO() {
    }
}
