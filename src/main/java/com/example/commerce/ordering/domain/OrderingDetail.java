package com.example.commerce.ordering.domain;

import com.example.commerce.common.domain.BaseTimeEntity;
import com.example.commerce.product.domain.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class OrderingDetail extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "ordering_id",foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),nullable = false)
    private Ordering ordering;
    @ManyToOne
    @JoinColumn(name = "product_id" ,foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),nullable = false)
    private Product product;

    private int quantity;
}
