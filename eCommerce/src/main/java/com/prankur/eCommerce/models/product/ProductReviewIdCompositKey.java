package com.prankur.eCommerce.models.product;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductReviewIdCompositKey implements Serializable
{
    private long customerId;
    private long productId;

    public ProductReviewIdCompositKey(long customerId, long productId) {
        this.customerId = customerId;
        this.productId = productId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "ProductReviewIdCompositKey{" +
                "customerId=" + customerId +
                ", productId=" + productId +
                '}';
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof ProductReviewIdCompositKey)) return false;
//        ProductReviewIdCompositKey that = (ProductReviewIdCompositKey) o;
//        return getCustomerId() == that.getCustomerId() &&
//                getProductId() == that.getProductId();
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getCustomerId(), getProductId());
//    }


    public ProductReviewIdCompositKey() {
    }
}
