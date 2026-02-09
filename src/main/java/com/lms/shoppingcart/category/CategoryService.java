package com.lms.shoppingcart.category;

import com.lms.shoppingcart.exception.AlreadyExistsException;
import com.lms.shoppingcart.exception.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategory(){

        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {

        return categoryRepository.findById(id)
                .orElseThrow(()-> new CategoryNotFoundException("Category Not Found of Id= "+ id ));
    }

    @Override
    public Category getByCategoryName(String name) {
        return categoryRepository.findByCategoryName(name);
    }

    @Override
    public Category addCategory(Category category) {

        return Optional.of(category)
                .filter(category1 -> !categoryRepository.existsByCategoryName(category.getCategoryName()))
                .map(categoryRepository :: save)
                .orElseThrow(()-> new AlreadyExistsException(category.getCategoryName()+" Already Exist"));
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        return null;
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete, ()->{
                    throw new CategoryNotFoundException("Category Not Found of ID ="+id);
                } );
    }

}
