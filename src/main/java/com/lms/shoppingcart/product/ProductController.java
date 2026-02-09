package com.lms.shoppingcart.product;


import com.lms.shoppingcart.dto.ProductDto;
import com.lms.shoppingcart.exception.ProductNotFoundException;
import com.lms.shoppingcart.request.AddProductRequest;
import com.lms.shoppingcart.request.ProductUpdateRequest;
import com.lms.shoppingcart.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/products")
public class ProductController {

    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> convertedProduct = productService.getConvertedProducts(products);
        return ResponseEntity.ok(new ApiResponse("Found", convertedProduct));
    }

    @GetMapping("/product/{productId}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
        try {
            Product product = productService.getProductById(productId);
            ProductDto convertedProduct = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Found", convertedProduct));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product theProduct = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("Uploaded", theProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long productId, @RequestBody ProductUpdateRequest request) {
        try {
            Product updatedProduct = productService.updateProductById(request, productId);
            ProductDto convertedProduct = productService.convertToDto(updatedProduct);
            return ResponseEntity.ok(new ApiResponse("updated", convertedProduct));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/product/{productId}/dalete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Deleted", productId));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/by/brand")
    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String  brand) {
        try {
            List<Product> products = productService.getProductsByBrand(brand);
            if(products.isEmpty())
            {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Products Not Found", null));
            }
            List<ProductDto> convertedProduct = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Found", convertedProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByProductNameAndBrand(@RequestParam String  brandName,@RequestParam String  productName) {
        try {
            List<Product> products = productService.getProductsByNameAndBrand(brandName,productName);
            if(products.isEmpty())
            {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Products Not Found", null));
            }
            List<ProductDto> convertedProduct = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Found", convertedProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by/name")
    public ResponseEntity<ApiResponse> getProductByProductName(@RequestParam String  productName) {
        try {
            List<Product> products = productService.getProductsByName(productName);
            if(products.isEmpty())
            {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Products Not Found", null));
            }
            List<ProductDto> convertedProduct = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Found", convertedProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by/category")
    public ResponseEntity<ApiResponse> getProductByCategoryName(@RequestParam String  category) {
        try {
            List<Product> products = productService.getProductsByCategory(category);
            if(products.isEmpty())
            {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Products Not Found", null));
            }
            List<ProductDto> convertedProduct = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Found", convertedProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByBrandAndCategoryName(@RequestParam String brand,@RequestParam String  categoryName) {
        try {
            List<Product> products = productService.getProductsByBrandAndCategory(brand,categoryName);
            if(products.isEmpty())
            {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Products Not Found", null));
            }
            List<ProductDto> convertedProduct = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Found", convertedProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @GetMapping("/count/by/brand-and-name")
    public ResponseEntity<ApiResponse> countProductByBrandAndName(@RequestParam String brand,@RequestParam String  productName) {
        try {
            var productCount = productService.countProductByBrandName(brand,productName);
            return ResponseEntity.ok(new ApiResponse("Product counted", productCount));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
         }
    }


}