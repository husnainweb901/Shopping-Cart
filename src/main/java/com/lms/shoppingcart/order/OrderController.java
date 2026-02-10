package com.lms.shoppingcart.order;


import com.lms.shoppingcart.dto.OrderDto;
import com.lms.shoppingcart.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/orders")
public class OrderController {
    private final IOrderService orderService;
    private final OrderRepository orderRepository;

    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId)
    {
        try {
            Order order = orderService.placeOrder(userId);
            OrderDto orderDto = orderService.convertToDto(order);
            return ResponseEntity.ok().body(new ApiResponse("Order Created Successfully",orderDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId)
    {
        try {
            OrderDto order = orderService.getOrder(orderId);
            return ResponseEntity.ok().body(new ApiResponse("Item Order Successfully",order));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Error Occurred", e.getMessage()));
        }
    }

    @GetMapping("/{userId}/order")
    public ResponseEntity<ApiResponse> getUserOrder(@PathVariable Long userId)
    {
        try {
            List<OrderDto> order = orderService.getUserOrder(userId);
            return ResponseEntity.ok().body(new ApiResponse("Item Order Successfully",order));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Error Occurred", e.getMessage()));
        }
    }

}
