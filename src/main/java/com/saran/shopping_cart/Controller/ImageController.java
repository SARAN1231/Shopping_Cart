package com.saran.shopping_cart.Controller;

import com.saran.shopping_cart.Dtos.ImageDto;
import com.saran.shopping_cart.Models.Images;
import com.saran.shopping_cart.Responses.ApiResponse;
import com.saran.shopping_cart.Services.Images.ImageService;
import com.saran.shopping_cart.Utils.ImageUtils;
import com.saran.shopping_cart.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> uploadImage(@RequestParam List<MultipartFile> files,@RequestParam Long productId) throws SQLException, IOException {
        try {
            List<ImageDto> imageList = imageService.saveImage(files,productId);
            return ResponseEntity.ok(new ApiResponse("Upload successful", imageList));
        } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed", e.getMessage()));
        }

    }


    @GetMapping("id/{imageId}")
    public ResponseEntity<?> getImageById(@PathVariable Long imageId) throws ResourceNotFoundException {
        byte[] image =  imageService.getImageById(imageId);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<?> downloadImage(@PathVariable Long imageId) {
        byte[] image =  imageService.getImageById(imageId);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
    }


    @PutMapping("/update/{imageId}")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId,@RequestParam MultipartFile file) throws SQLException, IOException {
        try {
            byte[] bytes = imageService.getImageById(imageId);
            if(bytes != null){
                imageService.updateImageById(imageId,file);
                return ResponseEntity.ok(new ApiResponse("update successful", file.getOriginalFilename()));
            }
        } catch (ResourceNotFoundException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Update failed", e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Update failed",null));
    }

    @DeleteMapping("/delete/{imageId}")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) throws SQLException, IOException {
        try{
           byte[] bytes = imageService.getImageById(imageId);
            if(bytes != null){
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse("Delete successful", imageId));
            }

        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Delete failed", e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete failed","Not found"));
    }
}
