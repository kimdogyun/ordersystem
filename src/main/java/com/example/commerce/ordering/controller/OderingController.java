package com.example.commerce.ordering.controller;

import com.example.commerce.ordering.service.OderingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ordering")
public class OderingController {
    private final OderingService oderingService;
    @Autowired
    public OderingController(OderingService oderingService) {
        this.oderingService = oderingService;
    }
    @PostMapping("/create")
    public Long create(){

    }
}
