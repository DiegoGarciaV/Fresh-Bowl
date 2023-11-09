package com.fciencias.freshbowl.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class ViewsController {

    @GetMapping("/inventory")
    public ModelAndView inventory() {
        return new ModelAndView("inventory/index");
    }

    @GetMapping("/inventory/manager")
    public ModelAndView inventoryManager() {
        return new ModelAndView("inventory/crud-form");
    }
    
}
