package com.lms.shoppingcart.cart;

import com.lms.shoppingcart.exception.CartNotFoundException;
import com.lms.shoppingcart.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {
    private final ICartService cartService;

    @GetMapping("/{cartId}/my-cart")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId)
    {
        try {
            Cart cart = cartService.getCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Find successfully", cart));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId){
        try {
            cartService.clearCart(cartId);
            return ResponseEntity.ok().body(new ApiResponse("Remove Successfully", null));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{cartId}/total-price")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cartId)
    {
        try {
            BigDecimal totalAmount = cartService.getTotalPrice(cartId);
            return  ResponseEntity.ok().body(new ApiResponse("Total price",totalAmount));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
