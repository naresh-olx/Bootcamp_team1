package com.projectalpha.projectalpha.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/V1")
public class HealthCheck {
    @GetMapping
    public String HealthOk() {
        return "Health is OK !! 👌🏻😎";
    }
}
