package com.saran.shopping_cart.Repository;

import com.saran.shopping_cart.Models.Images;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ImageRepository extends JpaRepository<Images, Long> {
}
