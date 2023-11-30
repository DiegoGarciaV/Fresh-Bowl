package com.fciencias.freshbowl.services.producttypes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.fciencias.freshbowl.models.ProductType;

@Repository
@Component
public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {

    ProductType findByTypeName(String typeName);
}
