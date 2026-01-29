package com.example.commerce.ordering.domain;

import com.example.commerce.product.domain.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class OrderingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ordering_id")
    private Ordering ordering;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int productCount;
}
