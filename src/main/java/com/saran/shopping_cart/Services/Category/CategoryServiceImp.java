package com.saran.shopping_cart.Services.Category;

import com.saran.shopping_cart.Models.Category;
import com.saran.shopping_cart.Repository.CategoryRepository;
import com.saran.shopping_cart.Requests.AddCategoryRequest;
import com.saran.shopping_cart.exceptions.AlreadyFoundException;
import com.saran.shopping_cart.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImp(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Category Not Found !"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(AddCategoryRequest categoryRequest) {
        Category category = new Category();
        if(categoryRepository.existsByName(categoryRequest.getCategoryName())) {
            throw new AlreadyFoundException("Category is already Found");
        }
        category.setName(categoryRequest.getCategoryName());

        return categoryRepository.save(category);
    }


    @Override
    public void updateCategory(AddCategoryRequest request, Long categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if(optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setName(request.getCategoryName());
             categoryRepository.save(category);
        }
        else  {
            throw new ResourceNotFoundException("Category Not Found !");
        }


    }

    @Override
    public void deleteCategory(Long id) {
            categoryRepository.findById(id)
                    .ifPresentOrElse(categoryRepository::delete,
                            ()->  {throw  new ResourceNotFoundException("Category Not Found !");});
    }
}
