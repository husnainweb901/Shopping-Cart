package com.lms.shoppingcart.cartItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

    void deleteAllByCart_CartID(Long cartId);
}
