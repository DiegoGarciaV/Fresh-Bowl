package com.fciencias.freshbowl.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fciencias.freshbowl.models.InventoryItem;
import com.fciencias.freshbowl.services.inventory.InventoryService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/items")
    public ResponseEntity<List<InventoryItem>> getAllItems() {
        List<InventoryItem> items = inventoryService.getAllInventoryItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<InventoryItem> getItemById(@PathVariable int itemId) {
        InventoryItem item = inventoryService.getInventoryItemById(itemId);
        if (item != null) {
            return new ResponseEntity<>(item, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addItem")
    public ResponseEntity<InventoryItem> addItem(@RequestBody InventoryItem item) {
        InventoryItem newItem = inventoryService.saveInventoryItem(item);
        return new ResponseEntity<>(newItem, HttpStatus.CREATED);
    }

    @PutMapping("/updateItem/{itemId}")
    public ResponseEntity<InventoryItem> updateItem(@PathVariable int itemId, @RequestBody InventoryItem updatedItem) {
        InventoryItem updated = inventoryService.updateInventoryItem(itemId, updatedItem);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteItem/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable int itemId) {
        inventoryService.deleteInventoryItem(itemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
