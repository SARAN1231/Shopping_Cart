package com.saran.shopping_cart.Services.Product;

import com.saran.shopping_cart.Models.Product;
import com.saran.shopping_cart.Requests.AddProductRequest;

import java.util.List;

public interface ProductService {

    Product addProduct(AddProductRequest request);
    Product getProductById(Long productId);
    void deleteProduct(Long productId);
    void updateProduct(AddProductRequest request,Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategoryName(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByNameAndBrand(String name, String brand);
    Long countProductByNameAndBrand(String name, String brand);
}
