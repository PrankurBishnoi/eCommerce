package com.prankur.eCommerce.cos;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class MetadataFieldCO
{
    @NotNull
    @NotEmpty
    @Column(unique = true)
    private String name;
    @NotNull
    @NotEmpty
    private Long parentCategoryId;

    public MetadataFieldCO(@NotNull @NotEmpty String name) {
        this.name = name;
    }

    public MetadataFieldCO(@NotNull @NotEmpty String name, @NotNull @NotEmpty Long parentCategoryId) {
        this.name = name;
        this.parentCategoryId = parentCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    @Override
    public String toString() {
        return "MetadataFieldCO{" +
                "name='" + name + '\'' +
                ", parentCategoryId=" + parentCategoryId +
                '}';
    }

    public MetadataFieldCO() {
    }
}
