package com.example.commerce.product.controller;

import com.example.commerce.product.domain.Product;
import com.example.commerce.product.dtos.ProductCreateDto;
import com.example.commerce.product.dtos.ProductDetailDto;
import com.example.commerce.product.dtos.ProductListdDto;
import com.example.commerce.product.dtos.ProductSearchDto;
import com.example.commerce.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/product")
public class productController {
    private final ProductService productService;
    @Autowired
    public productController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestPart("product") ProductCreateDto dto,
                                    @RequestPart("productImage")MultipartFile productImage){
        Product product=productService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(product.getId());

    }
    @GetMapping("/list")
    public Page<ProductListdDto>findAll(@PageableDefault(size = 10,sort="id",direction = Sort.Direction.DESC)
                                        Pageable pageable, @ModelAttribute ProductSearchDto searchDto){
        return productService.findAll(pageable,searchDto);

    }
    @GetMapping("/{id}")
    public ProductDetailDto findById(@PathVariable Long id){
        ProductDetailDto dto = productService.findById(id);
        return dto;
    }

}
