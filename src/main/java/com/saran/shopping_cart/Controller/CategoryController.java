package com.saran.shopping_cart.Controller;

import com.saran.shopping_cart.Models.Category;
import com.saran.shopping_cart.Requests.AddCategoryRequest;
import com.saran.shopping_cart.Responses.ApiResponse;
import com.saran.shopping_cart.Services.Category.CategoryService;
import com.saran.shopping_cart.exceptions.AlreadyFoundException;
import com.saran.shopping_cart.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all-category")
    public ResponseEntity<ApiResponse> getAllCategory() {
        List<Category> allCategory = categoryService.getAllCategories();
        return ResponseEntity.ok(new ApiResponse("Found !",allCategory));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId) {
        try {
            Category category = categoryService.getCategoryById(categoryId);
            if(category != null) {
                return ResponseEntity.ok(new ApiResponse("Found !",category));
            }
        } catch (ResourceNotFoundException e) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Not Found",null));
    }

    @GetMapping("/{categoryName}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String categoryName) {
        Category category = categoryService.getCategoryByName(categoryName);
        return ResponseEntity.ok(new ApiResponse("Found !",category));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody AddCategoryRequest category) {
       try{
            Category category1 =  categoryService.addCategory(category);
           return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Added !",category1));
       }
       catch (AlreadyFoundException e) {
           return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(),null));
       }
    }

    @PutMapping("Update/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long categoryId, @RequestBody AddCategoryRequest category) {
        try {
            Category categoryToUpdate = categoryService.getCategoryById(categoryId);
            if(categoryToUpdate != null) {
                categoryService.updateCategory(category, categoryId);
            }
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Category not found", HttpStatus.NOT_FOUND.value()));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Category not found", HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse("Deleted !",categoryId));
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Category not found", HttpStatus.NOT_FOUND.value()));
        }

    }
}
