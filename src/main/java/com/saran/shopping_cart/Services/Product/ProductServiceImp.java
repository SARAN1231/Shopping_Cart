package com.saran.shopping_cart.Services.Product;

import com.saran.shopping_cart.Models.Category;
import com.saran.shopping_cart.Models.Product;
import com.saran.shopping_cart.Repository.CategoryRepository;
import com.saran.shopping_cart.Repository.ProductRepository;
import com.saran.shopping_cart.Requests.AddProductRequest;
import com.saran.shopping_cart.exceptions.ProductNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImp(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Product addProduct(AddProductRequest request) {
            // check if the category is exists in DB
            // if yes, set it as new product in category // If Category Exists, Use It; Else, Create a New Category:
            // else create a new category
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(()-> {
                    Category newCategory = new Category();
                    newCategory.setName(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        Product product = new Product();
        product.setCategory(category);
        product.setPrice(request.getPrice());
        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setDescription(request.getDescription());
        product.setInventory(request.getInventory());

        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow( ()->new ProductNotFoundException("Product Not Found !"));
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.findById(productId)
                .ifPresentOrElse(productRepository::delete,
                        ()-> {throw  new ProductNotFoundException("Product Not Found !");});
    }

    @Override
    public void updateProduct(AddProductRequest request, Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if(productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setPrice(request.getPrice());
            product.setName(request.getName());
            product.setBrand(request.getBrand());
            product.setDescription(request.getDescription());
            product.setInventory(request.getInventory());
            Category category = categoryRepository.findByName(request.getCategory().getName());
            product.setCategory(category);
            productRepository.save(product);
        }
        else {
            throw new ProductNotFoundException("Product Not Found !");
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategoryName(String categoryName) {
        return productRepository.findByCategoryName(categoryName);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByNameAndBrand(String name, String brand) {
        return productRepository.findByNameAndBrand(name, brand);
    }

    @Override
    public Long countProductByNameAndBrand(String name, String brand) {
        return productRepository.countByNameAndBrand(name, brand);
    }
}
