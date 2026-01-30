package com.example.commerce.ordering.controller;

import com.example.commerce.ordering.dtos.OderingCreateDto;
import com.example.commerce.ordering.dtos.OrderingListDto;
import com.example.commerce.ordering.service.OrderingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody List<OderingCreateDto> dtoList) {
        orderingService.create(dtoList);

    }

    @GetMapping("/list")
    @PreAuthorize(("hasRole('ADMIN')"))
    public List<OrderingListDto> list() {
        return orderingService.findAll();
    }

    @GetMapping("/myorders")
    public List<OrderingListDto> myorders() {
        return orderingService.findMyOrders();
    }
}
