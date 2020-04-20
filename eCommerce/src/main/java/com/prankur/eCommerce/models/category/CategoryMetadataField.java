package com.prankur.eCommerce.models.category;

import javax.persistence.*;
import java.util.Set;

@Entity
public class CategoryMetadataField
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;

    @OneToMany(mappedBy = "categoryMetadataField",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<MetadataFieldValues> metadataFieldValues;

    public CategoryMetadataField(long id, String name, Set<MetadataFieldValues> metadataFieldValues) {
        this.id = id;
        this.name = name;
        this.metadataFieldValues = metadataFieldValues;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<MetadataFieldValues> getMetadataFieldValues() {
        return metadataFieldValues;
    }

    public void setMetadataFieldValues(Set<MetadataFieldValues> metadataFieldValues) {
        this.metadataFieldValues = metadataFieldValues;
    }

    @Override
    public String toString() {
        return "CategoryMetadataField{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", metadataFieldValues=" + metadataFieldValues +
                '}';
    }

    public CategoryMetadataField() {
    }
}
