package com.projectalpha.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Health-Check")
@RestController
@RequestMapping("/api/V1")
public class HealthCheck {
    @GetMapping
    public String HealthOk() {
        return "Health is OK !! 👌🏻😎";
    }
}
