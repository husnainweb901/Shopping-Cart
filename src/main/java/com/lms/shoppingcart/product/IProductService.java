package com.lms.shoppingcart.product;

import com.lms.shoppingcart.request.AddProductRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String categoryName);
    List<Product> getProductByBrand(String Brand);
    List<Product> getProductByBrandAndCategory(String brand, String categoryName);
    List<Product> getProductByName(String productName);
    List<Product> getProductByNameAndBrand(String productName, String brand);
    Product getProductById(Long id);
    void deleteProductById(Long Id);
    void updateProductById(Product product,Long id);
    Long countProductByBrandName(String brand, String productName);
}
