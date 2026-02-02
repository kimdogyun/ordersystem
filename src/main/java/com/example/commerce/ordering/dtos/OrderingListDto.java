package com.example.commerce.ordering.dtos;

import com.example.commerce.ordering.domain.OrderStatus;
import com.example.commerce.ordering.domain.Ordering;
import com.example.commerce.ordering.domain.OrderingDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderingListDto {
    private Long id;
    private String memberEmail;
    private OrderStatus orderStatus;
    private List<OrderDetailDto> orderDetails;

    public static OrderingListDto fromEntity(Ordering ordering) {
        List<OrderDetailDto> orderDetailDtos = new ArrayList<>();
        for (OrderingDetail orderDetail : ordering.getOrderingDetailsList()){
            orderDetailDtos.add(OrderDetailDto.fromEntity(orderDetail));
        }
        OrderingListDto orderingListDto = OrderingListDto.builder()
                .id(ordering.getId())
                .memberEmail(ordering.getMember().getEmail())
                .orderStatus(ordering.getOrderStatus())
                .orderDetails(orderDetailDtos)
                .build();
        return orderingListDto;
    }
}
