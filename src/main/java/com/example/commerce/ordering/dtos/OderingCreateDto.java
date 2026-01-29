package com.example.commerce.ordering.dtos;

import com.example.commerce.ordering.domain.Ordering;
import com.example.commerce.ordering.domain.OrderingDetail;
import com.example.commerce.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OderingCreateDto {
    private Long productId ;
    private int productCount;


    public OrderingDetail toEntity(Ordering ordering, Product product) {
        return OrderingDetail.builder()
                .ordering(ordering)
                .product(product)
                .productCount(this.productCount)
                .build();
    }
}
