package com.lms.shoppingcart.category;

import com.lms.shoppingcart.exception.AlreadyExistsException;
import com.lms.shoppingcart.exception.CategoryNotFoundException;
import com.lms.shoppingcart.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getCategory() {
        try {
            List<Category> categories = categoryService.getAllCategory();
            return ResponseEntity.ok(new ApiResponse("Found", categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error:", INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name) {
        try {
            Category theCategory = categoryService.addCategory(name);
            return ResponseEntity.ok(new ApiResponse("Successful", theCategory));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{categoryId}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId) {
        try {
            Category theCategory = categoryService.getCategoryById(categoryId);
            return ResponseEntity.ok(new ApiResponse("Found", theCategory));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{categoryName}/category")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String categoryName) {
        try {
            Category theCategory = categoryService.getByCategoryName(categoryName);
            return ResponseEntity.ok(new ApiResponse("Found", theCategory));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/category/{categoryId}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok(new ApiResponse("Deleted", true));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/category/{categoryId}/update")
    public ResponseEntity<ApiResponse> updateCategory(@RequestBody Category category,@PathVariable Long categoryId) {
        try {
            Category updateCategory = categoryService.updateCategory(category,categoryId);
            return ResponseEntity.ok(new ApiResponse("Updated", updateCategory));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
