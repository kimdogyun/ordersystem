package com.example.commerce.product.service;

import com.example.commerce.product.domain.Product;
import com.example.commerce.product.dtos.ProductCreateDto;
import com.example.commerce.product.dtos.ProductDetailDto;
import com.example.commerce.product.dtos.ProductListDto;
import com.example.commerce.product.dtos.ProductSearchDto;
import com.example.commerce.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(ProductCreateDto dto) {
        Product product = dto.toEnntity();
        return productRepository.save(product);
    }

    public Page<ProductListDto> findAll(Pageable pageable, ProductSearchDto searchdto) {
        Specification<Product> specification = new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                if (searchdto.getName() != null) {
                    predicateList.add(criteriaBuilder.like(root.get("name"), "%" + searchdto.getName() + "%"));

                }
                if (searchdto.getCategory() != null) {
                    predicateList.add(criteriaBuilder.like(root.get("category"), "%" + searchdto.getCategory() + "%"));
                }
                Predicate[] predicateArr = new Predicate[predicateList.size()];
                for (int i = 0; i < predicateArr.length; i++) {
                    predicateArr[i] = predicateList.get(i);
                }
                Predicate predicate = criteriaBuilder.and(predicateArr);
                return predicate;

            }
        };
        Page<Product> productPage = productRepository.findAll(specification, pageable);
        return productPage.map(p -> ProductListDto.fromEntity(p));


    }

    public ProductDetailDto findById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        Product product = optionalProduct.orElseThrow(() -> new EntityNotFoundException("X"));
        ProductDetailDto dto = ProductDetailDto.fromEntity(product);
        return dto;
    }
}

