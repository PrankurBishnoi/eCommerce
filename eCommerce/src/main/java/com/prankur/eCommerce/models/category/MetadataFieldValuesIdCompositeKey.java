package com.prankur.eCommerce.models.category;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MetadataFieldValuesIdCompositeKey implements Serializable
{
    private long categoryId;
    private long metadataFieldId;

    public MetadataFieldValuesIdCompositeKey(long categoryId, long metadataFieldId) {
        this.categoryId = categoryId;
        this.metadataFieldId = metadataFieldId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getMetadataFieldId() {
        return metadataFieldId;
    }

    public void setMetadataFieldId(long metadataFieldId) {
        this.metadataFieldId = metadataFieldId;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof MetadataFieldValuesIdCompositeKey)) return false;
//        MetadataFieldValuesIdCompositeKey that = (MetadataFieldValuesIdCompositeKey) o;
//        return getCategoryId() == that.getCategoryId() &&
//                getMetadataFieldId() == that.getMetadataFieldId();
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getCategoryId(), getMetadataFieldId());
//    }

    @Override
    public String toString() {
        return "MetadataFieldValuesIdCompositeKey{" +
                "categoryId=" + categoryId +
                ", metadataFieldId=" + metadataFieldId +
                '}';
    }

    public MetadataFieldValuesIdCompositeKey() {
    }
}
