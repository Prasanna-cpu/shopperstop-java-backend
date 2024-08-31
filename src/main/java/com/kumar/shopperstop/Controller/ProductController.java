package com.kumar.shopperstop.Controller;


import com.kumar.shopperstop.Exceptions.EmptyDataException;
import com.kumar.shopperstop.Exceptions.ProductNotFoundException;
import com.kumar.shopperstop.Model.Product.Product;
import com.kumar.shopperstop.Request.AddProductRequest;
import com.kumar.shopperstop.Request.UpdateProductRequest;
import com.kumar.shopperstop.Response.ApiResponse;
import com.kumar.shopperstop.Service.Product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("all")
    public ResponseEntity<ApiResponse> getAllProducts() throws EmptyDataException {

        List<Product> products=productService.getAllProducts();
        try{

            return ResponseEntity.ok(new ApiResponse("Products found",products));
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Operation failed",e.getMessage()));
        }
    }

    @GetMapping("product/{productId}")
    public ResponseEntity<ApiResponse> getProductsById(@PathVariable Long productId) throws ProductNotFoundException {

        Product product=productService.getProductById(productId);

        try{
            return ResponseEntity.ok(new ApiResponse("Product found",product));
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Operation failed",e.getMessage()));
        }

    }

    @PostMapping("add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
        try{
            Product addedProduct=productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("Product added",addedProduct));
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Operation failed",e.getMessage()));
        }

    }
    @PutMapping("/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody UpdateProductRequest product, @PathVariable Long productId) throws ProductNotFoundException {

        Product updatedProduct=productService.updateProduct(product,productId);

        try{

            return ResponseEntity.ok(new ApiResponse("Product updated",updatedProduct));
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Operation failed",e.getMessage()));
        }

    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) throws ProductNotFoundException {

        productService.deleteProduct(productId);

        try{

            return ResponseEntity.ok(new ApiResponse("Product deleted",null));
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Operation failed",e.getMessage()));
        }

    }

    @GetMapping("/brand/{brandName}/name/{productName}")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@PathVariable String brandName,@PathVariable String productName) throws ProductNotFoundException, EmptyDataException {
        List<Product> products=productService.getProductsByBrandAndName(brandName,productName);
        try{
            return ResponseEntity.ok(new ApiResponse("Products found",products));
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Operation failed",e.getMessage()));
        }
    }


    @GetMapping("/name/{productName}")
    public ResponseEntity<ApiResponse> getProductsByName(@PathVariable String productName) throws ProductNotFoundException, EmptyDataException {

        List<Product> products=productService.getProductsByName(productName);
        try{
            return ResponseEntity.ok(new ApiResponse("Products found",products));
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Operation failed",e.getMessage()));
        }

    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<ApiResponse> getProductsByBrand(@PathVariable String brand) throws EmptyDataException, ProductNotFoundException {
        List<Product> products=productService.getProductsByBrand(brand);
        try{
            return ResponseEntity.ok(new ApiResponse("Products found",products));
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Operation failed",e.getMessage()));
        }
    }

    @GetMapping("/products/category/{category}")
    public ResponseEntity<ApiResponse> getProductsByCategory(@PathVariable String category) throws EmptyDataException, ProductNotFoundException {

        List<Product> products=productService.getProductsByCategory(category);
        try{
            return ResponseEntity.ok(new ApiResponse("Products found",products));
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Operation failed",e.getMessage()));
        }

    }

    @GetMapping("/brand/{brand}/category/{category}")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(@PathVariable String brand , @PathVariable String category) throws EmptyDataException, ProductNotFoundException {

        List<Product> products=productService.getProductsByCategoryAndBrand(brand,category);

        try{
            return ResponseEntity.ok(new ApiResponse("Products found",products));
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Operation failed",e.getMessage()));
        }

    }


    @GetMapping("/count-name-brand")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name){
        Long count= productService.countProductsByBrandAndName(brand,name);
        try{
            return ResponseEntity.ok(new ApiResponse("Products found",count));
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Operation failed",e.getMessage()));
        }

    }











}
