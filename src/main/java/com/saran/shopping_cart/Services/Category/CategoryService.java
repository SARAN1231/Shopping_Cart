package com.saran.shopping_cart.Services.Category;

import com.saran.shopping_cart.Models.Category;
import com.saran.shopping_cart.Requests.AddCategoryRequest;

import java.util.List;

public interface CategoryService {

    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(AddCategoryRequest category);

    void updateCategory(AddCategoryRequest request, Long categoryId);

    void deleteCategory(Long id);

}
