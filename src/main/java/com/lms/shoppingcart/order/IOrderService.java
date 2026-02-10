package com.lms.shoppingcart.order;

import com.lms.shoppingcart.dto.OrderDto;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrder(Long userId);

    OrderDto convertToDto(Order order);
}
