package com.example.commerce.ordering.repository;

import com.example.commerce.member.domain.Member;
import com.example.commerce.ordering.domain.OrderingDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderingDetailRepository extends JpaRepository <OrderingDetail, Long> {
}
