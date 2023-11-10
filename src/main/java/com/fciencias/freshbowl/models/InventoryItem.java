package com.fciencias.freshbowl.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class InventoryItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int itemId;
    @NotNull(message = "No se ha informado el nombre del producto")
    protected String name;
    @ManyToOne
    @JoinColumn(name="item_type")
    protected ProductType itemType;
    protected int quantity;
    protected double price;
    @ManyToOne
    @JoinColumn(name="unit")
    protected Unit unit;
    protected String img;
    @NotNull(message = "No se ha informado fecha de adquisicion.")
    protected LocalDate acquisitionDate;
    @NotNull(message = "No se ha informado fecha de caducidad.")
    @FutureOrPresent(message = "La fecha de caducidad debe ser en el futuro o en el presente")
    protected LocalDate expiryDate;
    protected String description;
    protected String comments;
    @NotBlank(message = "No se ha informado proveedor.")
    protected String provider;


    @AssertTrue(message = "La fecha de caducidad debe ser posterior a la fecha de adquisicion.")
    public boolean isAcquisitionAfterExpiry() {
        if (acquisitionDate == null || expiryDate == null) {
            return true;
        }
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
