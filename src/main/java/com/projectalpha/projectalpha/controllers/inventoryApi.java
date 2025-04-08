package com.projectalpha.projectalpha.controllers;

import com.projectalpha.projectalpha.model.data;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class inventoryApi {

    @GetMapping("/health-ok")
    public String HealthOk(){
        return "Health is OK !! 👌🏻😎";
    }

    @PostMapping()
    public String Post(@RequestBody data data){
        return "ok";
    }


}
