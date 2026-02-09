package com.lms.shoppingcart.dto;

import com.lms.shoppingcart.category.Category;
import lombok.Data;


import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {

    private Long productId;
    private String productName;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;


    private Category category;
    private List<ImageDto> images;
}
