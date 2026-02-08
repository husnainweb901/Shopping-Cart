package com.lms.shoppingcart.request;

import com.lms.shoppingcart.category.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddProductRequest {
    private Long productId;
    private String productName;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
