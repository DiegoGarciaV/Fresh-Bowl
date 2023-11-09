package com.fciencias.freshbowl.services.inventory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fciencias.freshbowl.models.InventoryItem;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository)
    {
        this.inventoryRepository = inventoryRepository;
    }
    
    public List<InventoryItem> getAllInventoryItems() {
        return inventoryRepository.findAll();
    }

    public InventoryItem getInventoryItemById(int id) {
        return inventoryRepository.findById(id).orElse(null);
    }

    public InventoryItem saveInventoryItem(InventoryItem inventoryItem) {
        return inventoryRepository.save(inventoryItem);
    }

    public InventoryItem updateInventoryItem(int itemId, InventoryItem updatedItem) {
        InventoryItem existingItem = inventoryRepository.findById(itemId).orElse(null);

        if (existingItem != null) {
            
            updatedItem.setItemId(itemId);
            return inventoryRepository.save(updatedItem);
        } else {
            return null;
        }
    }

    public void deleteInventoryItem(int id) {
        inventoryRepository.deleteById(id);
    }
}
