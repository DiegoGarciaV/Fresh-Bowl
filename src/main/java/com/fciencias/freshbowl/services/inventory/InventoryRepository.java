package com.fciencias.freshbowl.services.inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.fciencias.freshbowl.models.InventoryItem;

@Repository
@Component
public interface InventoryRepository extends JpaRepository<InventoryItem, Integer> {

}
