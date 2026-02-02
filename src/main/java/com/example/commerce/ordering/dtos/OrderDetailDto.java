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
    public static OrderDetailDto fromEntity(OrderingDetail orderingDetail){
        return OrderDetailDto.builder()
                .detailId(orderingDetail.getId())
                .productName(orderingDetail.getProduct().getName())
                .productcount(orderingDetail.getQuantity())
                .build();
    }
}
