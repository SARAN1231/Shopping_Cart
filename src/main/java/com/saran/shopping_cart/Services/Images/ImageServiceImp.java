package com.saran.shopping_cart.Services.Images;

import com.saran.shopping_cart.Dtos.ImageDto;
import com.saran.shopping_cart.Models.Images;
import com.saran.shopping_cart.Models.Product;
import com.saran.shopping_cart.Repository.ImageRepository;
import com.saran.shopping_cart.Repository.ProductRepository;
import com.saran.shopping_cart.Services.Product.ProductServiceImp;
import com.saran.shopping_cart.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageServiceImp implements ImageService {

    private final ImageRepository imageRepository;
    private final ProductServiceImp productService;
    public ImageServiceImp(ImageRepository imageRepository, ProductRepository productRepository, ProductServiceImp productService) {
        this.imageRepository = imageRepository;
        this.productService = productService;

    }
    @Override
    public Images getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No image found with id: " + id));
    }

    @Override
    public void deleteImageById(Long id) {
            imageRepository.findById(id)
                    .ifPresentOrElse(imageRepository::delete, () -> {
                        throw new ResourceNotFoundException("No image found with id: " + id);
                    });
    }

    @Override
    public void updateImageById(Long imageId, MultipartFile image) {

    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) throws IOException, SQLException {
        Product product = productService.getProductById(productId);
        List<ImageDto> imageDtos = new ArrayList<>();
        for (MultipartFile file : files) {
            Images image = new Images();
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setProduct(product);
            image.setImage(new SerialBlob(file.getBytes()));

            String BuildDownloadUrl = "/api/v1/images/image/download/";
            String downloadUrl = BuildDownloadUrl + image.getId();
            image.setDownloadUrl(downloadUrl);

            Images savedImage = imageRepository.save(image);
            savedImage.setDownloadUrl(BuildDownloadUrl+savedImage.getId());
            imageRepository.save(savedImage);

            ImageDto imageDto = new ImageDto(savedImage.getId(), savedImage.getFileName(), savedImage.getFileType(), savedImage.getDownloadUrl());
            imageDtos.add(imageDto);
        }
        return imageDtos;
    }
}
