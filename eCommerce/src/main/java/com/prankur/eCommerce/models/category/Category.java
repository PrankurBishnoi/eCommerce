package com.prankur.eCommerce.models.category;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.prankur.eCommerce.models.product.Product;

import javax.persistence.*;
import java.util.Set;

@Entity
@JsonFilter(value = "CategoryFilter")
public class Category
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToOne()
    @JoinColumn(name = "parent_id")
    private Category parentCategory =null;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Product> products = null;

    @OneToMany(mappedBy = "category",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<MetadataFieldValues> metadataFieldValues;


    public Category(String name, Category parentCategory, Set<Product> products) {
        this.name = name;
        this.parentCategory = parentCategory;
        this.products = products;
    }

    public Category(String name, Set<Product> products, Set<MetadataFieldValues> metadataFieldValues) {
        this.name = name;
        this.products = products;
        this.metadataFieldValues = metadataFieldValues;
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, Category parentCategory) {
        this.name = name;
        this.parentCategory = parentCategory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentCategory=" + parentCategory +
                ", products=" + products +
                '}';
    }

    public Category() {
    }
}
