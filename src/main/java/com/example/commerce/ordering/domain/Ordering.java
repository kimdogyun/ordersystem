package com.example.commerce.ordering.domain;

import com.example.commerce.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Ordering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.ORDERED;
    @OneToMany(mappedBy = "ordering", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default // 캐스케이딩
    private List<OrderingDetail> orderingDetailsList = new ArrayList<>();

}
