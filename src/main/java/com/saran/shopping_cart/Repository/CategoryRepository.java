package com.saran.shopping_cart.Repository;

import com.saran.shopping_cart.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);
    Boolean existsByName(String name);
}
