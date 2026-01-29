package com.example.commerce.ordering.service;

import com.example.commerce.member.domain.Member;
import com.example.commerce.member.repository.MemberRepository;
import com.example.commerce.ordering.domain.OrderStatus;
import com.example.commerce.ordering.domain.Ordering;
import com.example.commerce.ordering.domain.OrderingDetail;
import com.example.commerce.ordering.dtos.OderingCreateDto;
import com.example.commerce.ordering.repository.OrderingDetailRepository;
import com.example.commerce.ordering.repository.OrderingRepository;
import com.example.commerce.product.domain.Product;
import com.example.commerce.product.repository.ProductRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class OrderingService {
    private final OrderingRepository orderingRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final OrderingDetailRepository orderingDetailRepository;

    @Autowired
    public OrderingService(OrderingRepository orderingRepository, MemberRepository memberRepository, ProductRepository productRepository, OrderingDetailRepository orderingDetailRepository) {
        this.orderingRepository = orderingRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
        this.orderingDetailRepository = orderingDetailRepository;


    }

    public Long create(String email, List<OderingCreateDto> dtoList) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("X"));
        Ordering ordering = Ordering.builder()
                .member(member)
                .orderStatus(OrderStatus.ORDERED)
                .build();
        orderingRepository.save(ordering);
        for (OderingCreateDto dto : dtoList) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("X"));

            OrderingDetail detail = dto.toEntity(ordering, product);
            orderingDetailRepository.save(detail);

        }
        return ordering.getId();


    }
}
