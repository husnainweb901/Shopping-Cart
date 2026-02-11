package com.lms.shoppingcart.cartItem;

import com.lms.shoppingcart.cart.Cart;
import com.lms.shoppingcart.cart.ICartService;
import com.lms.shoppingcart.exception.CartNotFoundException;
import com.lms.shoppingcart.response.ApiResponse;
import com.lms.shoppingcart.user.IUserService;
import com.lms.shoppingcart.user.User;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final IUserService userService;

    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemsToCart(@RequestParam Long productId,
                                                      @RequestParam int quantity) {
        try {
            User user = userService.getAuthenticatedUser();
            Cart cart = cartService.initializeNewCart(user);
            cartItemService.addItemToCart(cart.getCartID(), productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Add successfully", null));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }catch (JwtException e){
            return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse(e.getMessage(),null));
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
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

}
