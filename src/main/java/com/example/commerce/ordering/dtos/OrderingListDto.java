package com.example.commerce.ordering.dtos;

import com.example.commerce.ordering.domain.OrderStatus;
import com.example.commerce.ordering.domain.Ordering;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderingListDto {
    private Long id;
    private String memberEmail;
    private OrderStatus orderStatus;

    public static OrderingListDto fromEntity(Ordering ordering) {
        return OrderingListDto.builder()
                .id(ordering.getId())
                .memberEmail(ordering.getMember().getEmail())
                .orderStatus(ordering.getOrderStatus())
                .build();
    }

}
