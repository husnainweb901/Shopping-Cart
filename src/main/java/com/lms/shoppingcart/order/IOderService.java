package com.lms.shoppingcart.order;

import java.util.List;

public interface IOderService {
    Order placeOrder(Long userId);
    Order getOrder(Long orderId);

    List<Order> getUserOrder(Long userId);
}
