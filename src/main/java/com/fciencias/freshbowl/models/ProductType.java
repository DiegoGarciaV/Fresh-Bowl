package com.fciencias.freshbowl.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "product_types")
public class ProductType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ProductTypeId;
    private String typeName;
    public int getProductTypeId() {
        return ProductTypeId;
    }
    public void setProductTypeId(int productTypeId) {
        ProductTypeId = productTypeId;
    }
    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    
}
