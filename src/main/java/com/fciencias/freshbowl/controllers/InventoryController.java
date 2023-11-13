package com.fciencias.freshbowl.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fciencias.freshbowl.models.InventoryItem;
import com.fciencias.freshbowl.models.ProductType;
import com.fciencias.freshbowl.models.Unit;
import com.fciencias.freshbowl.services.inventory.InventoryService;
import com.fciencias.freshbowl.services.producttypes.ProductTypeRepository;
import com.fciencias.freshbowl.services.units.UnitRepository;

@RestController
@RequestMapping("/")
public class InventoryController {


    private final InventoryService inventoryService;

    private final UnitRepository unitRepository;

    private final ProductTypeRepository productTypesRepository;

    @Autowired
    public InventoryController(InventoryService inventoryService, UnitRepository unitService, ProductTypeRepository productTypeService) {
        this.inventoryService = inventoryService;
        this.unitRepository = unitService;
        this.productTypesRepository = productTypeService;
    }

    @GetMapping("/inventory")
    public ModelAndView inventory() {

        ModelAndView response = new ModelAndView("inventory/index");
        List<InventoryItem> items = inventoryService.getAllInventoryItems();
        response.addObject("items", items);
        return response;
    }

    @GetMapping("/inventory/manager/{itemId}")
    public ModelAndView inventoryUpdate(@PathVariable int itemId) {

        ModelAndView response = new ModelAndView("inventory/crud-form");
        InventoryItem item = inventoryService.getInventoryItemById(itemId);
        if(item != null)
        {
            response.addObject("item", item);
        }
        else
        {
            item = new InventoryItem();
            item.setItemType(new ProductType());
            item.setUnit(new Unit());
            response.addObject("item", item);
        }
        return response;
    }

    @GetMapping("/inventory/manager")
    public ModelAndView inventoryManager() {

        ModelAndView response = new ModelAndView("inventory/crud-form");

        InventoryItem item = new InventoryItem();
        item.setItemType(new ProductType());
        item.setUnit(new Unit());

        response.addObject("item", item);
        return response;
    }

    @PostMapping(value = "/inventory/manager", consumes = "application/x-www-form-urlencoded")
    @Validated
    public ModelAndView inventoryPostManager(@Valid @ModelAttribute("item") InventoryItem item,  BindingResult bindingResult) 
    {
        ModelAndView response = new ModelAndView("inventory/crud-form");
        if(item.getImg() == null)
            item.setImg("imgs/ingredients/");
            
        if (bindingResult.hasErrors()) 
        {
            
            Map<String,Object> errors = new HashMap<>();
            Map<String,String> fieldsNamesMapper = new HashMap<>();

            fieldsNamesMapper.put("expiryDate", "Fecha de caducidad");
            fieldsNamesMapper.put("acquisitionDate", "Fecha de adquisicion");
            fieldsNamesMapper.put("provider", "Provedor");
            fieldsNamesMapper.put("itemId", "Id");
            fieldsNamesMapper.put("quantity", "Cantidad");
            fieldsNamesMapper.put("price", "Precio");
            fieldsNamesMapper.put("acquisitionAfterExpiry", "Inconsistencia en fechas");
            fieldsNamesMapper.put("name", "Nombre del producto");

            for (FieldError error : bindingResult.getFieldErrors()) 
            {
                if(fieldsNamesMapper.containsKey(error.getField()))
                    errors.put(fieldsNamesMapper.get(error.getField()), error.getDefaultMessage());
                else
                    errors.put(error.getField(), error.getDefaultMessage());
            }

            response.addObject("errors", errors);
            response.addObject("item", item);
        }
        else
        {
            Unit unit = unitRepository.findByUnitName(item.getUnit().getUnitName());
            item.setUnit(unit);
            ProductType productType = productTypesRepository.findByTypeName(item.getItemType().getTypeName());
            item.setItemType(productType);
            InventoryItem newItem;
            if(item.getItemId() == 0)
            {
                System.out.println("Creando registro");
                newItem = inventoryService.saveInventoryItem(item);
                System.out.println("Creado: " + newItem.getItemId());
            }
            else
            {
                System.out.println("Actualizando registro: " + item.getItemId());
                newItem = inventoryService.updateInventoryItem(item.getItemId(), item);
            }
            response.addObject("item", newItem);
            response.addObject("successMessage", "Se ha actualizado exitosamente el articulo");
        }
        
        return response;
    }

    
    
}
