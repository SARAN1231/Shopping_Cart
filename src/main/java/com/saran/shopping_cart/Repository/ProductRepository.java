package com.saran.shopping_cart.Repository;

import com.saran.shopping_cart.Models.Category;
import com.saran.shopping_cart.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryName(String category_name);
    List<Product> findByBrand(String brand);
    List<Product> findByCategoryNameAndBrand(String category_name, String brand);
    List<Product> findByName(String product_name);
    List<Product> findByNameAndBrand(String product_name, String brand);
    Long countByNameAndBrand(String product_name, String brand);
}
