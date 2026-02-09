package com.lms.shoppingcart.category;

import java.util.List;

public interface ICategoryService {
    List<Category> getAllCategory();
    Category getCategoryById(Long id);
    Category getByCategoryName(String name);
    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);
    void deleteCategory(Long id);
}
