package com.fciencias.freshbowl.services.inventory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fciencias.freshbowl.models.InventoryItem;

public interface InventoryRepository extends JpaRepository<InventoryItem,Integer> {
    
}
