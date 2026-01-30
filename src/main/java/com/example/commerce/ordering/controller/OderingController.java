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
    public ResponseEntity<?> create(@RequestBody List<OderingCreateDto> dtoList) {
        Long id = orderingService.create(dtoList);
        return ResponseEntity.status(HttpStatus.OK).body(id);

    }

    @GetMapping("/list")
    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<?> list() {
        List<OrderingListDto> orderingListDtos = orderingService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(orderingListDtos);
    }

    @GetMapping("/myorders")
    public ResponseEntity<?> myorders() {
        List<OrderingListDto>orderingListDtos = orderingService.findMyOrders();
        return ResponseEntity.status(HttpStatus.OK).body(orderingListDtos);
    }
}
