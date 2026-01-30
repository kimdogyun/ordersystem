package com.example.commerce.ordering.service;

import com.example.commerce.member.domain.Member;
import com.example.commerce.member.repository.MemberRepository;
import com.example.commerce.ordering.domain.OrderStatus;
import com.example.commerce.ordering.domain.Ordering;
import com.example.commerce.ordering.domain.OrderingDetail;
import com.example.commerce.ordering.dtos.OderingCreateDto;
import com.example.commerce.ordering.dtos.OrderingListDto;
import com.example.commerce.ordering.repository.OrderingDetailRepository;
import com.example.commerce.ordering.repository.OrderingRepository;
import com.example.commerce.product.domain.Product;
import com.example.commerce.product.repository.ProductRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
    public Long create( List<OderingCreateDto> orderCreateDtoList){
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Member member = memberRepository.findByEmail(email).orElseThrow(()->new EntityNotFoundException("member is not found"));
        Ordering ordering = Ordering.builder()
                .member(member)
                .build();
        for (OderingCreateDto dto : orderCreateDtoList){
            Product product = productRepository.findById(dto.getProductId()).orElseThrow(()->new EntityNotFoundException("entity is not found"));
            OrderingDetail orderDetail = OrderingDetail.builder()
                    .ordering(ordering)
                    .product(product)
                    .quantity(dto.getProductCount())
                    .build();
            ordering.getOrderingDetailsList().add(orderDetail);
        }
        orderingRepository.save(ordering);


        return ordering.getId();
    }

    public List<OrderingListDto> findAll() {

        List<Ordering> orderingList = orderingRepository.findAll();
        return orderingRepository.findAll().stream().map(o->OrderingListDto.fromEntity(o)).collect(Collectors.toList());
//        List<OrderingListDto> dtoList = new ArrayList<>();
//        for (Ordering o : orderingList) {
//            OrderingListDto dto = OrderingListDto.fromEntity(o);
//            dtoList.add(dto);
//        }
//        return dtoList;
    }

    public List<OrderingListDto> findMyOrders() {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        List<Ordering> orderingList = orderingRepository.findByMemberEmail(email);
        List<OrderingListDto> dtoList = new ArrayList<>();
        for (Ordering o : orderingList) {
            OrderingListDto dto = OrderingListDto.fromEntity(o);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
