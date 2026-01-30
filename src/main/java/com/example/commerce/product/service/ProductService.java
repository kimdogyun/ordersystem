package com.example.commerce.product.service;

import com.example.commerce.member.domain.Member;
import com.example.commerce.member.repository.MemberRepository;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final S3Client s3Client;
    @Value("${aws.s3.bucket1}")
    private String bucket;

    @Autowired
    public ProductService(ProductRepository productRepository, MemberRepository memberRepository, S3Client s3Client) {
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
        this.s3Client = s3Client;
    }

    public Long save(ProductCreateDto dto, MultipartFile productImage) {
        Product product = dto.toEntity();
        Member admin = memberRepository.findById(1L).orElseThrow();
        product.setMember(admin);
        productRepository.save(product);
//        if (productImage != null) {
//            String fileName = "product-" + product.getId() + "-productimage-" + productImage.getOriginalFilename();
//            PutObjectRequest request = PutObjectRequest.builder()
//                    .bucket(bucket)
//                    .key(fileName)
//                    .contentType(productImage.getContentType())
//                    .build();
//            try {
//                s3Client.putObject(request, RequestBody.fromBytes(productImage.getBytes()));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            String imgUrl = s3Client.utilities()
//                    .getUrl(a -> a.bucket(bucket).key(fileName))
//                    .toExternalForm();
//
//            product.updateProductImage(imgUrl);
//
//        }
        return product.getId();
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

