package com.lms.shoppingcart.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lms.shoppingcart.category.Category;
import com.lms.shoppingcart.image.Image;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonBackReference
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> image;

    public Product(String brand, Category category, String description, BigDecimal price, String productName, int inventory) {
        this.brand = brand;
        this.category = category;
        this.description = description;
        this.price = price;
        this.productName = productName;
        this.inventory = inventory;
    }
}
