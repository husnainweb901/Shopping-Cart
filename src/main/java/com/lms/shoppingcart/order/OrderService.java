package com.lms.shoppingcart.order;

import com.lms.shoppingcart.cart.Cart;
import com.lms.shoppingcart.cart.CartService;
import com.lms.shoppingcart.dto.OrderDto;
import com.lms.shoppingcart.enums.OrderStaus;
import com.lms.shoppingcart.exception.ResourceNotFoundException;
import com.lms.shoppingcart.orderItem.OrderItem;
import com.lms.shoppingcart.product.Product;
import com.lms.shoppingcart.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = creatOder(cart);
        List<OrderItem> orderItemList = creatOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setOrderTotalAmount(calculateTotalAmount(orderItemList));
        Order saveOrder = orderRepository.save(order);
        cartService.clearCart(cart.getCartID());

        return saveOrder;
    }

    private Order creatOder(Cart cart)
    {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStaus(OrderStaus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private List<OrderItem> creatOrderItems(Order order, Cart cart)
    {
        return cart.getItems()
                .stream()
                .map(cartItem -> {
                    Product product = cartItem.getProduct();
                    product.setInventory(product.getInventory() - cartItem.getQuantity());
                    productRepository.save(product);
                    return new OrderItem(
                            order,
                            product,
                            cartItem.getQuantity(),
                            cartItem.getUnitPrice()
                    );
                }).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList)
    {
        return orderItemList
                .stream()
                .map(item ->item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToDto)
                .orElseThrow(()-> new ResourceNotFoundException("Order Not Found"));
    }

    @Override
    public List<OrderDto> getUserOrder(Long userId)
    {
        List<Order> orders = orderRepository.findByUserUserId(userId);
        return orders
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public OrderDto convertToDto(Order order){
        return modelMapper.map(order, OrderDto.class);
    }
}
