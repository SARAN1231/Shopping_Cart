package com.saran.shopping_cart.Controller;

import com.saran.shopping_cart.Models.Product;
import com.saran.shopping_cart.Requests.AddProductRequest;
import com.saran.shopping_cart.Responses.ApiResponse;
import com.saran.shopping_cart.Services.Product.ProductService;
import com.saran.shopping_cart.exceptions.ProductNotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
@Tag(name = "Product", description = "The product API") // for swaggerUI
// link - > http://localhost:8080/swagger-ui/index.html#/
public class ProductController {

    private final ProductService productService;
    public ProductController(ProductService productService)
    {
        this.productService = productService;
    }

    @GetMapping("/all-products")
    public ResponseEntity<ApiResponse> getAllProducts() {
        try {
            return ResponseEntity.ok(new ApiResponse("Successfully retrieved all products", productService.getAllProducts()));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Not found", e.getMessage()));
        }
    }

    @GetMapping("/id/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
        try{
            Product product = productService.getProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Successfully retrieved product", product));
        }
        catch (ProductNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Not found", e.getMessage()));
        }
    }

    @PostMapping("add-product")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product product1 = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("Successfully added product", product1));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", e.getMessage()));
        }
    }




    @PutMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long productId, @RequestBody AddProductRequest product) {
        try{
            Product product1 = productService.updateProduct(product,productId);
            return ResponseEntity.ok(new ApiResponse("Successfully updated product", product1));
        }
        catch (ProductNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Not found", e.getMessage()));
        }
    }

    @DeleteMapping("delete/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok(new ApiResponse("Successfully deleted product", productId));
        }
        catch (ProductNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Not found", e.getMessage()));
        }
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String categoryName) {
        try{
            List<Product> products = productService.getProductsByCategoryName(categoryName);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Not found", categoryName));
            }
            return ResponseEntity.ok(new ApiResponse("Product retrieved successfully",products));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @GetMapping("/brand/{brandName}")
    public ResponseEntity<ApiResponse> getProductByBrand(@PathVariable String brandName) {
        try{
            List<Product> products = productService.getProductsByBrand(brandName);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Not found", brandName));
            }
            return ResponseEntity.ok(new ApiResponse("Product retrieved successfully",products));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @GetMapping("/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String categoryName, @RequestParam String brandName) {
        try{
            List<Product> products = productService.getProductsByCategoryAndBrand(categoryName,brandName);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Not found", categoryName));
            }
            return ResponseEntity.ok(new ApiResponse("Product retrieved successfully",products));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error", e.getMessage()));
        }
    }
    @GetMapping("/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String name) {
        try {
            List<Product> products = productService.getProductsByNameAndBrand(name,brandName);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Not found", brandName));
            }
            return ResponseEntity.ok(new ApiResponse("Product retrieved successfully",products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @GetMapping("{productName}")
    public ResponseEntity<ApiResponse> getProductByProductName(@PathVariable String productName) {
        try{
            List<Product> products = productService.getProductsByName(productName);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Not found", productName));
            }
            return ResponseEntity.ok(new ApiResponse("Product retrieved successfully",products));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @GetMapping("/count/name-and-brand")
    public ResponseEntity<ApiResponse> getProductCountByNameAndBrand(@RequestParam String name, @RequestParam String brandName) {
        try{
            Long count = productService.countProductByNameAndBrand(name, brandName);
            if(count == 0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Not found", name));
            }
            return ResponseEntity.ok(new ApiResponse("Product count",count));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error", e.getMessage()));
        }
    }

}
