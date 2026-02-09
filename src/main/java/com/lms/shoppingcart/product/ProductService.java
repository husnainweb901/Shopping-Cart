package com.lms.shoppingcart.product;

import com.lms.shoppingcart.category.Category;
import com.lms.shoppingcart.category.CategoryRepository;
import com.lms.shoppingcart.dto.ImageDto;
import com.lms.shoppingcart.dto.ProductDto;
import com.lms.shoppingcart.exception.ProductNotFoundException;
import com.lms.shoppingcart.image.Image;
import com.lms.shoppingcart.image.ImageRepository;
import com.lms.shoppingcart.request.AddProductRequest;
import com.lms.shoppingcart.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;

    @Override
    public Product addProduct(AddProductRequest request) {

        Category category = Optional.ofNullable(categoryRepository.findByCategoryName(request.getCategory().getCategoryName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getCategoryName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getBrand(),
                category,
                request.getDescription(),
                request.getPrice(),
                request.getProductName(),
                request.getInventory()
        );
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String categoryName) {
        return productRepository.findByCategory_CategoryName(categoryName);
    }


    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByBrandAndCategory(String brand, String categoryName) {
        return productRepository.findByBrandAndCategory_CategoryName(brand, categoryName);
    }

    @Override
    public List<Product> getProductsByName(String productName) {
        return productRepository.findProductByProductName(productName);
    }

    @Override
    public List<Product> getProductsByNameAndBrand(String productName, String brand) {
        return productRepository.findByProductNameAndBrand(productName, brand);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not  found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete,
                () -> {
                    throw new ProductNotFoundException("Product Not Found");
                });
    }

    @Override
    public Product updateProductById(ProductUpdateRequest request, Long id) {
        return productRepository.findById(id)
                .map(existingProduct -> updateExistingProduct(existingProduct,request))
                .map(productRepository :: save)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found"));
    }

    public Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request){
        existingProduct.setProductName(request.getProductName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        Category category = categoryRepository.findByCategoryName(request.getCategory().getCategoryName());
        existingProduct.setCategory(category);

        return existingProduct;
    }

    @Override
    public Long countProductByBrandName(String brand, String productName) {
        return productRepository.countByBrandAndProductName(brand, productName);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products)
    {
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product){
        ProductDto productDto = modelMapper.map(product,ProductDto.class);
        List<Image> images = imageRepository.findByProduct_ProductId(product.getProductId());
        List<ImageDto> imageDtos = images.stream()
                .map(image -> modelMapper.map(image,ImageDto.class))
                .toList();
        productDto.setImages(imageDtos);
        return productDto;
    }

}
