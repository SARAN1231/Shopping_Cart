package com.saran.shopping_cart.Models;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileType;
    @Lob
    private byte[] image;
    private String downloadUrl;

    @ManyToOne
    @JoinColumn(name = "Product_id")
    private Product product;

}

//public ResponseEntity<String> uploadImage(MultipartFile file) throws IOException {
//    imageRepo.save(ImageModel.builder()
//            .fileName(file.getOriginalFilename())
//            .fileType(file.getContentType())
//            .imageData(ImageUtils.compressImage(file.getBytes())).build()); // static function in util class no need to inject it
//    return org.springframework.http.ResponseEntity.ok("Image uploaded" + file.getOriginalFilename());
//}
//
//@Transactional // for handling large size files (only in postgres)
//public byte[] downloadImage(String fileName) throws IOException {
//    Optional<ImageModel> dbImage = imageRepo.findByFileName(fileName);
//    if (dbImage.isPresent()) {
//        byte[] image = ImageUtils.decompressImage(dbImage.get().getImageData());
//        return image;
//    }
//    return null;
//}
