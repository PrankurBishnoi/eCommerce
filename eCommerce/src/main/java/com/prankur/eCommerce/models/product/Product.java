package com.prankur.eCommerce.models.product;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.prankur.eCommerce.models.category.Category;
import com.prankur.eCommerce.models.users.Seller;

import javax.persistence.*;
import java.util.Set;

@Entity
@JsonFilter(value = "ProductFilter")
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String name;
    private String description;
    private boolean isCancellable = true;
    private boolean isReturnable = true;
    @Column(nullable = false)
    private String Brand;
    private boolean isActive = false;
    private boolean isDeleted = false;

    @OneToOne
    @JoinColumn(name = "seller_id",nullable = false)
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<ProductVariation> productVariations;

    @OneToMany(mappedBy = "product",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<ProductReview> productReviews;


    public Product(Seller seller, String name, String description, Category category, boolean isCancellable, boolean isReturnable, String brand, boolean isActive) {
        this.seller = seller;
        this.name = name;
        this.description = description;
        this.category = category;
        this.isCancellable = isCancellable;
        this.isReturnable = isReturnable;
        Brand = brand;
        this.isActive = isActive;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isCancellable() {
        return isCancellable;
    }

    public void setCancellable(boolean cancellable) {
        isCancellable = cancellable;
    }

    public boolean isReturnable() {
        return isReturnable;
    }

    public void setReturnable(boolean returnable) {
        isReturnable = returnable;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Set<ProductVariation> getProductVariations() {
        return productVariations;
    }

    public void setProductVariations(Set<ProductVariation> productVariations) {
        this.productVariations = productVariations;
    }

//    public Set<ProductReview> getProductReviews() {
//        return productReviews;
//    }
//
//    public void setProductReviews(Set<ProductReview> productReviews) {
//        this.productReviews = productReviews;
//    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", seller=" + seller +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", isCancellable=" + isCancellable +
                ", isReturnable=" + isReturnable +
                ", Brand='" + Brand + '\'' +
                ", isActive=" + isActive +
                '}';
    }

    public Product() {
    }
}
