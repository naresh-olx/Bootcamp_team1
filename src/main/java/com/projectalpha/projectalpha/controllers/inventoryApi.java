package com.projectalpha.projectalpha.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class inventoryApi {

    @GetMapping("/health-ok")
    public String HealthOk(){
        return "Health is OK !! 👌🏻😎";
    }


}
