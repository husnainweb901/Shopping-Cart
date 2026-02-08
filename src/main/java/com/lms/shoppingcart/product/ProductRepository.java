package com.lms.shoppingcart.product;

import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory_CategoryName(String categoryCategoryName);
    List<Product> findByBrand(String brand);
    List<Product> findByProductNameAndBrand(String productName, String brand);
    List<Product> findByBrandAndCategory_CategoryName(String brand, String categoryName);
    List<Product> findProductByProductName(String productName);
    Long countByBrandAndProductName(String brand, String productName);
}
