package com.example.commerce.product.controller;

import com.example.commerce.product.domain.Product;
import com.example.commerce.product.dtos.ProductCreateDto;
import com.example.commerce.product.dtos.ProductDetailDto;
import com.example.commerce.product.dtos.ProductListDto;
import com.example.commerce.product.dtos.ProductSearchDto;
import com.example.commerce.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping("/create")
    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<?> create(@ModelAttribute ProductCreateDto dto,
                       @RequestPart("productImage") MultipartFile productImage) {
        Long id = productService.save(dto, productImage);

        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }
    @GetMapping("/list")
    public Page<ProductListDto> findAll(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)
                                        Pageable pageable, @ModelAttribute ProductSearchDto searchDto) {
        return productService.findAll(pageable, searchDto);
    }
    @GetMapping("/{id}")
    public ProductDetailDto findById(@PathVariable Long id) {
        ProductDetailDto dto = productService.findById(id);
        return dto;
    }
}
