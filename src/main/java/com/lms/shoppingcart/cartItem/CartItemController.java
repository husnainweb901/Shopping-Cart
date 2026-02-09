package com.lms.shoppingcart.cartItem;

import com.lms.shoppingcart.cart.ICartService;
import com.lms.shoppingcart.exception.CartNotFoundException;
import com.lms.shoppingcart.response.ApiResponse;
import jakarta.persistence.PostRemove;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;

    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemsToCart(@RequestParam(required = false) Long cartId,
                                                      @RequestParam Long productId,
                                                      @RequestParam int quantity) {
        try {
            if(cartId == null){
                cartId = cartService.initializeNewCart();
            }
            cartItemService.addItemToCart(cartId, productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Add successfully", null));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/cart/{cartId}/items/{productId}/remove")
    public ResponseEntity<ApiResponse> removeItemsFormCart(@PathVariable Long cartId, @PathVariable Long productId) {
        try {
            cartItemService.removeItemFromCart(cartId, productId);
            return ResponseEntity.ok(new ApiResponse("Remove Successfully", null));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
                                                          @PathVariable Long itemId,
                                                          @RequestParam int quantity) {
        try {
            cartItemService.updateItemQuantity(cartId, itemId, quantity);
            return ResponseEntity.ok().body(new ApiResponse("Cart Update Successfully", null));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

}
