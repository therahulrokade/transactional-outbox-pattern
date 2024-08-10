package com.freecodebucket.common.mapper;

import com.freecodebucket.common.dto.OrderRequestDTO;
import com.freecodebucket.entity.Order;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class OrderDTOtoEntityMapper {
    public Order map (OrderRequestDTO orderRequestDTO) {
        return Order.builder()
                .name(orderRequestDTO.getName())
                .customerId(orderRequestDTO.getCustomerId())
                .productType(orderRequestDTO.getProductType())
                .quantity(orderRequestDTO.getQuantity())
                .price(orderRequestDTO.getPrice())
                .orderDate(new Date())
                .build();
    }
}
