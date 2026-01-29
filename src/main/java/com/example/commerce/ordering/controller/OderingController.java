package com.example.commerce.ordering.controller;

import com.example.commerce.ordering.dtos.OderingCreateDto;
import com.example.commerce.ordering.service.OrderingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordering")
public class OderingController {
    private final OrderingService orderingService;
    @Autowired
    public OderingController(OrderingService orderingService) {
        this.orderingService = orderingService;
    }
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody List<OderingCreateDto>dtoList){
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Long orderingId = orderingService.create(email, dtoList);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderingId);

    }
}
