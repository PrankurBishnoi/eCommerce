package com.prankur.eCommerce.dtos;

import com.prankur.eCommerce.models.category.Category;

import java.util.List;
import java.util.Set;

public class ViewCategoryDTO
{
    private List<Category> parentCategory;
    private Category category;
    private Category childCategory;



    public List<Category> getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(List<Category> parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getChildCategory() {
        return childCategory;
    }

    public void setChildCategory(Category childCategory) {
        this.childCategory = childCategory;
    }

    public ViewCategoryDTO() {
    }
}
