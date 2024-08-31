package com.kumar.shopperstop.Service.Product;


import com.kumar.shopperstop.Exceptions.EmptyDataException;
import com.kumar.shopperstop.Exceptions.ProductNotFoundException;
import com.kumar.shopperstop.Model.Category.Category;
import com.kumar.shopperstop.Model.Product.Product;
import com.kumar.shopperstop.Repository.Category.CategoryRepository;
import com.kumar.shopperstop.Repository.Product.ProductRepository;
import com.kumar.shopperstop.Request.AddProductRequest;
import com.kumar.shopperstop.Request.UpdateProductRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class ProductServiceImplementation implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private Product updateExistingProduct(UpdateProductRequest request,Product product) throws ProductNotFoundException {
        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setInventory(request.getInventory());

        Category category=categoryRepository.findByName(request.getCategory().getName()).orElseThrow(()->new ProductNotFoundException("Given category not found"));
        product.setCategory(category);
        return product;
    }


    private Product createProduct(AddProductRequest request, Category category){
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getDescription(),
                request.getInventory(),
                category
        );
    }


    /**
     * @param request
     * @return
     */
    @Override
    public Product addProduct(AddProductRequest request) {
//        Category category= Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
//                .orElseGet(
//                        ()->{
//                            Category newCategory=new Category(request.getCategory().getName());
//                            return categoryRepository.save(newCategory);
//                        }
//                );
//        request.setCategory(category);
//        return productRepository.save(createProduct(request,category));

        boolean isCategoryPresent=categoryRepository.existsByName(request.getCategory().getName());

        if(!isCategoryPresent){
            Category newCategory=new Category(request.getCategory().getName());
            categoryRepository.save(newCategory);
            request.setCategory(newCategory);
            return productRepository.save(createProduct(request,newCategory));
        }
        else{
            return null;
        }

    }

    /**
     * @return
     */
    @Override
    public List<Product> getAllProducts() throws EmptyDataException {
        List<Product> products=productRepository.findAll();

        if(products.isEmpty()){
            throw new EmptyDataException("No products found");
        }
        else{
            return products;
        }
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        Product product=productRepository.findById(id).orElseThrow(()->new ProductNotFoundException("Given product not found"));
        return product;
    }


    /**
     * @param request
     * @return
     */
    @Override
    public Product updateProduct(UpdateProductRequest request,Long productId) throws ProductNotFoundException {
        return productRepository.findById(productId)
                .map(existingProduct-> {
                    try {
                        return updateExistingProduct(request,existingProduct);
                    } catch (ProductNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(productRepository::save)
                .orElseThrow(()->new ProductNotFoundException("Given product not found"));
    }

    /**
     * @param id
     */
    @Override
    public void deleteProduct(Long id) throws ProductNotFoundException {
        Product product=productRepository.findById(id).orElseThrow(()->new ProductNotFoundException("Given product not found"));
        productRepository.delete(product);
    }

    /**
     * @param category
     * @return
     */
    @Override
    public List<Product> getProductsByCategory(String category) throws EmptyDataException {
        List<Product> products=productRepository.findByCategory(category);
        if(products.isEmpty()){
            throw new EmptyDataException("No products of given category was found");
        }
        return products;
    }

    /**
     * @param brand
     * @return
     */
    @Override
    public List<Product> getProductsByBrand(String brand) throws EmptyDataException {
        List<Product> products=productRepository.findByBrand(brand);
        if(products.isEmpty()){
            throw new EmptyDataException("No products of given brand was found");
        }
        return products;
    }

    /**
     * @param category
     * @param brand
     * @return
     */
    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) throws EmptyDataException {
        List<Product> products=productRepository.findByCategoryAndBrand(category,brand);
        if(products.isEmpty()){
            throw new EmptyDataException("No products of given category and brand was found");
        }
        return products;
    }

    /**
     * @param name
     * @param name
     * @return
     */
    @Override
    public List<Product> getProductsByName(String name) throws EmptyDataException {
        List<Product> products=productRepository.findByName(name);
        if(products.isEmpty()){
            throw new EmptyDataException("No products of given name was found");
        }
        return products;

    }

    /**
     * @param category
     * @param name
     * @return
     */
    @Override
    public List<Product> getProductsByBrandAndName(String category, String name) throws EmptyDataException {
        List<Product> products=productRepository.findByBrandAndName(category,name);
        if(products.isEmpty()){
            throw new EmptyDataException("No products of given brand or name was found");
        }
        return products;
    }

    /**
     * @param brand
     * @param name
     * @return
     */
    @Override
    public Long countProductsByBrandAndName(String brand,String name) {
        Long count=productRepository.countByBrandAndName(brand,name);
        return count;
    }
}
