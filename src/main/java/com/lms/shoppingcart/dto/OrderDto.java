package com.lms.shoppingcart.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class OrderDto {

    private Long orderId;
    private Long userId;
    private LocalDate orderDate;
    private BigDecimal orderTotalAmount;
    private String orderStaus;
    private Set<OrderItemDto> orderItems = new HashSet<>();
}
