package com.prankur.eCommerce.models.product;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prankur.eCommerce.utils.MetaDataConverter;

import javax.persistence.*;
import java.util.Map;

@Entity
@JsonFilter(value = "ProductVariationFilter")
public class ProductVariation
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Long quantityAvailable;
    private Double price;
    private boolean isActive = false;
    private boolean isDeleted = false;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Convert(converter = MetaDataConverter.class)
    private Map<String, String> jsonMetaData;

    public ProductVariation(Long quantityAvailable, Double price, boolean isActive, Product product, Map<String, String> jsonMetaData) {
        this.quantityAvailable = quantityAvailable;
        this.price = price;
        this.isActive = isActive;
        this.product = product;
        this.jsonMetaData = jsonMetaData;
    }

    public ProductVariation(Long quantityAvailable, Double price, boolean isActive) {
        this.quantityAvailable = quantityAvailable;
        this.price = price;
        this.isActive = isActive;
    }

    public ProductVariation(Long quantityAvailable, Double price) {
        this.quantityAvailable = quantityAvailable;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(Long quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Map<String, String> getJsonMetaData() {
        return jsonMetaData;
    }

    public void setJsonMetaData(Map<String, String> jsonMetaData) {
        this.jsonMetaData = jsonMetaData;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "ProductVariation{" +
                "id=" + id +
                ", quantityAvailable=" + quantityAvailable +
                ", price=" + price +
                ", isActive=" + isActive +
                ", product=" + product +
                ", jsonMetaData=" + jsonMetaData +
                '}';
    }


    public ProductVariation() {
    }
}
