package com.projectalpha.projectalpha.controllers;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class InventoryController {

    @GetMapping("/health-ok")
    public String HealthOk(){
        return "Health is OK !! 👌🏻😎";
    }



}
