package com.fciencias.freshbowl.services.units;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.fciencias.freshbowl.models.Unit;

@Repository
@Component
public interface UnitRepository extends JpaRepository<Unit, Integer> {

    Unit findByUnitName(String unitName);

}
