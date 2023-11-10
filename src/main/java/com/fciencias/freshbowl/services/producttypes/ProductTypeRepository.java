package com.fciencias.freshbowl.services.producttypes;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fciencias.freshbowl.models.ProductType;

public interface ProductTypeRepository extends JpaRepository<ProductType,Integer>{

    ProductType findByTypeName(String typeName);
}
