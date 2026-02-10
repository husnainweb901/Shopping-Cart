package com.lms.shoppingcart.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    private Long cartItemId;
    private int quantity;
    private BigDecimal unitPrice;
    private  ProductDto product;
}
