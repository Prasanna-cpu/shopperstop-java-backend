package com.kumar.shopperstop.Service.Product;

import com.kumar.shopperstop.DTO.ProductDTO;
import com.kumar.shopperstop.Exceptions.EmptyDataException;
import com.kumar.shopperstop.Exceptions.ExistingProductException;
import com.kumar.shopperstop.Exceptions.ProductNotFoundException;
import com.kumar.shopperstop.Model.Product.Product;
import com.kumar.shopperstop.Request.AddProductRequest;
import com.kumar.shopperstop.Request.UpdateProductRequest;

import java.util.List;

public interface ProductService {


    ProductDTO mapToProductDTO(Product product);

    List<ProductDTO> getConvertedProducts(List<Product> products);

    Product addProduct(AddProductRequest request) throws ProductNotFoundException, ExistingProductException;

    List<Product> getAllProducts() throws EmptyDataException;

    Product getProductById(Long id) throws ProductNotFoundException;

//    List<Product> getProductByCategoryId(Long categoryId);

    Product updateProduct(UpdateProductRequest product, Long productId) throws ProductNotFoundException;

    void deleteProduct(Long id) throws ProductNotFoundException;

    List<Product> getProductsByCategory(String category) throws EmptyDataException, ProductNotFoundException;

    List<Product> getProductsByBrand(String brand) throws EmptyDataException, ProductNotFoundException;

    List<Product> getProductsByCategoryAndBrand(String category, String brand) throws EmptyDataException, ProductNotFoundException;

    List<Product> getProductsByName(String name) throws EmptyDataException, ProductNotFoundException;

    List<Product> getProductsByBrandAndName(String category,String name) throws EmptyDataException, ProductNotFoundException;

    Long countProductsByBrandAndName(String brand,String name);


}
