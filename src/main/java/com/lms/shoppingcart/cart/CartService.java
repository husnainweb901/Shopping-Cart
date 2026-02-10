package com.lms.shoppingcart.cart;

import com.lms.shoppingcart.cartItem.CartItemRepository;
import com.lms.shoppingcart.exception.CartNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart getCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart Not Found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void clearCart(Long cartId) {
        Cart cart = getCart(cartId);
        cartItemRepository.deleteAllByCart_CartID(cartId);
        cart.getItems().clear();
        cartRepository.deleteById(cartId);
    }

    @Override
    public BigDecimal getTotalPrice(Long cartId) {
        Cart cart = getCart(cartId);
        return cart.getTotalAmount();
    }

    @Override
    public Long initializeNewCart() {
        Cart newCart = new Cart();

        newCart.setTotalAmount(BigDecimal.ZERO);
        Cart saved = cartRepository.save(newCart);
        return saved.getCartID();
    }

    @Override
    public Cart getCartByUserId(Long userId) {

        return cartRepository.findByUser_UserId(userId);
    }
}
