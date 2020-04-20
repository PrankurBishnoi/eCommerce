package com.prankur.eCommerce.models.category;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class MetadataFieldValues
{

    @EmbeddedId
    private MetadataFieldValuesIdCompositeKey id;

    private String value;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @MapsId("metadataFieldId")
    private CategoryMetadataField categoryMetadataField;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @MapsId("categoryId")
    private Category category;

    public MetadataFieldValues(String value) {
        this.value = value;
    }

    public MetadataFieldValues(MetadataFieldValuesIdCompositeKey id, String value) {
        this.id = id;
        this.value = value;
    }

    public MetadataFieldValues(MetadataFieldValuesIdCompositeKey id, String value, CategoryMetadataField categoryMetadataField, Category category) {
        this.id = id;
        this.value = value;
        this.categoryMetadataField = categoryMetadataField;
        this.category = category;
    }

    public MetadataFieldValuesIdCompositeKey getId() {
        return id;
    }

    public void setId(MetadataFieldValuesIdCompositeKey id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CategoryMetadataField getCategoryMetadataField() {
        return categoryMetadataField;
    }

    public void setCategoryMetadataField(CategoryMetadataField categoryMetadataField) {
        this.categoryMetadataField = categoryMetadataField;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "MetadataFieldValues{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", categoryMetadataField=" + categoryMetadataField +
                ", category=" + category +
                '}';
    }

    public MetadataFieldValues() {
    }
}
