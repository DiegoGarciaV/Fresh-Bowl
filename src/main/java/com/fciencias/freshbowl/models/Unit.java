package com.fciencias.freshbowl.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "units")
public class Unit {

    private int unitId;
    private String unitName;
    private boolean unitType;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getUnitId() {
        return unitId;
    }
    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }
    public String getUnitName() {
        return unitName;
    }
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
    public boolean isUnitType() {
        return unitType;
    }
    public void setUnitType(boolean unitType) {
        this.unitType = unitType;
    }
    
}
