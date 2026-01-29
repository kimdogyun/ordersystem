package com.example.commerce.product.service;

import com.example.commerce.product.domain.Product;
import com.example.commerce.product.dtos.ProductCreateDto;
import com.example.commerce.product.dtos.ProductListdDto;
import com.example.commerce.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public Product save(ProductCreateDto dto){
        Product product = dto.toEnntity();
        return product;
    }
    public Page<ProductListdDto> findAll(Pageable pageable,ProductListdDto dto) {

    }
}
