package com.example.commerce.ordering.dtos;

import com.example.commerce.ordering.domain.OrderingDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailDto {
    private Long detailId;
    private String productName;
    private  int productcount;
    public OrderDetailDto fromEntity(OrderingDetail orderingDetail){
        return OrderDetailDto.builder()
                .detailId(orderingDetail.getId())
                .productName(orderingDetail.getN)
                .productcount(orderingDetail.getProductCount())
                .build();
    }
}
