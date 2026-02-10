package com.lms.shoppingcart.cart;

import com.lms.shoppingcart.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUser_UserId(Long userUserId);
}
