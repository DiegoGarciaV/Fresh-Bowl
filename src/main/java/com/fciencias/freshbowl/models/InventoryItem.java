package com.fciencias.freshbowl.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class InventoryItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemId;
    @NotNull(message = "No se ha informado el nombre del producto")
    @NotBlank(message = "No se ha informado el nombre del producto")
    private String name;
    @ManyToOne
    @JoinColumn(name="item_type")
    @NotNull(message = "No se ha informado el tipo del producto")
    private ProductType itemType;
    private int quantity;
    private double price;
    @ManyToOne
    @JoinColumn(name="unit")
    @NotNull(message = "No se ha informado unidad de medida")
    private Unit unit;
    private String img;
    @NotNull(message = "No se ha informado fecha de adquisicion.")
    private LocalDate acquisitionDate;
    private LocalDate expiryDate;
    private String description;
    private String comments;
    @NotBlank(message = "No se ha informado proveedor.")
    private String provider;


    @AssertTrue(message = "No se ha informado fecha de caducidad para producto perecedero")
    public boolean isPerishable() {
        if(itemType == null)
            return false;
        if (itemType.getTypeName() == "Perecedero") {
            System.out.println(itemType.getTypeName());
            return expiryDate != null;
        }
        expiryDate = null;
        return true;
    }

    @AssertTrue(message = "La fecha de caducidad debe informarse y ser posterior a la fecha de adquisicion.")
    public boolean isAcquisitionAfterExpiry() {
        if (itemType != null && itemType.getTypeName() != "Perecedero") {
            return true;
        }
        if(expiryDate == null)
            return false;

        return expiryDate.isAfter(acquisitionDate);
    }

    public int getItemId() {
        return itemId;
    }
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ProductType getItemType() {
        return itemType;
    }
    public void setItemType(ProductType itemType) {
        this.itemType = itemType;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public Unit getUnit() {
        return unit;
    }
    public void setUnit(Unit unit) {
        this.unit = unit;
    }
    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public LocalDate getAcquisitionDate() {
        return acquisitionDate;
    }
    public void setAcquisitionDate(LocalDate acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    public String getProvider() {
        return provider;
    }
    public void setProvider(String provider) {
        this.provider = provider;
    }

    

}
