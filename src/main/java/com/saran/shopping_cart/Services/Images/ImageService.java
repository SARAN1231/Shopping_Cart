package com.saran.shopping_cart.Services.Images;

import com.saran.shopping_cart.Dtos.ImageDto;
import com.saran.shopping_cart.Models.Images;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ImageService {

    Images getImageById(Long id);
    void deleteImageById(Long id);
    void updateImageById(Long imageId, MultipartFile image) throws IOException, SQLException;
    List<ImageDto> saveImage(List<MultipartFile> image, Long productId) throws IOException, SQLException;
}
