package com.lms.shoppingcart.product;

import com.lms.shoppingcart.dto.ProductDto;
import com.lms.shoppingcart.request.AddProductRequest;
import com.lms.shoppingcart.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String categoryName);
    List<Product> getProductsByBrand(String Brand);
    List<Product> getProductsByBrandAndCategory(String brand, String categoryName);
    List<Product> getProductsByName(String productName);
    List<Product> getProductsByNameAndBrand(String productName, String brand);
    Product getProductById(Long id);
    void deleteProductById(Long Id);
    Product updateProductById(ProductUpdateRequest request, Long id);
    Long countProductByBrandName(String brand, String productName);

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);
}
