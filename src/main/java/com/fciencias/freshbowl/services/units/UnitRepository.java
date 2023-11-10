package com.fciencias.freshbowl.services.units;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fciencias.freshbowl.models.Unit;

public interface UnitRepository extends JpaRepository<Unit,Integer>{

    Unit findByUnitName(String unitName);
    
}
