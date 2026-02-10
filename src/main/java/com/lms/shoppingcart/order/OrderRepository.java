package com.lms.shoppingcart.order;

import com.lms.shoppingcart.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUserUserId(Long userId);

    Long user(User user);
}
