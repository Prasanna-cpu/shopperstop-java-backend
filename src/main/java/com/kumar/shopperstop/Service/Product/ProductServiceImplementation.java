package com.kumar.shopperstop.Service.Product;


import com.kumar.shopperstop.DTO.ImageDTO;
import com.kumar.shopperstop.DTO.ProductDTO;
import com.kumar.shopperstop.Exceptions.EmptyDataException;
import com.kumar.shopperstop.Exceptions.ExistingProductException;
import com.kumar.shopperstop.Exceptions.ProductNotFoundException;
import com.kumar.shopperstop.Model.Category.Category;
import com.kumar.shopperstop.Model.Image.Image;
import com.kumar.shopperstop.Model.Product.Product;
import com.kumar.shopperstop.Repository.Category.CategoryRepository;
import com.kumar.shopperstop.Repository.Image.ImageRepository;
import com.kumar.shopperstop.Repository.Product.ProductRepository;
import com.kumar.shopperstop.Request.AddProductRequest;
import com.kumar.shopperstop.Request.UpdateProductRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class ProductServiceImplementation implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;

    @Override
    public ProductDTO mapToProductDTO(Product product){
        ProductDTO productDTO=modelMapper.map(product,ProductDTO.class);
        List<Image> images=imageRepository.findByProductId(product.getId());

        List<ImageDTO> imageDTOS=images.stream().map(
                image->modelMapper.map(image,ImageDTO.class)
        ).collect(Collectors.toList());
        productDTO.setImages(imageDTOS);
        return productDTO;
    }

    @Override
    public List<ProductDTO> getConvertedProducts(List<Product> products){
        return products.stream().map(this::mapToProductDTO).collect(Collectors.toList());
    }


    private boolean productExists(String name,String brand){

        return productRepository.existsByNameAndBrand(name,brand);

    }



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
    public Product addProduct(AddProductRequest request) throws ProductNotFoundException, ExistingProductException {
//        Category category= Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
//                .orElseGet(
//                        ()->{
//                            Category newCategory=new Category(request.getCategory().getName());
//                            return categoryRepository.save(newCategory);
//                        }
//                );
//        request.setCategory(category);
//        return productRepository.save(createProduct(request,category));

        if(productExists(request.getName(),request.getBrand())){
            throw new ExistingProductException("Product already exists: "+request.getName()+" "+request.getBrand());
        }


        Category category;
        if (!categoryRepository.existsByName(request.getCategory().getName())) {
            category = new Category(request.getCategory().getName());
            categoryRepository.save(category);
        } else {
            category = categoryRepository.findByName(request.getCategory().getName()).orElseThrow(()->new ProductNotFoundException("Given category not found"));
        }
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));

    }

    /**
     * @return
     */
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
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
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
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
    public List<Product> getProductsByCategory(String category) throws EmptyDataException, ProductNotFoundException {
        List<Product> products=productRepository.findByCategoryName(category).orElseThrow(()->new ProductNotFoundException("Given product not found"));
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
    public List<Product> getProductsByBrand(String brand) throws EmptyDataException, ProductNotFoundException {
        List<Product> products=productRepository.findByBrand(brand).orElseThrow(()->new ProductNotFoundException("Given product not found"));
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
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) throws EmptyDataException, ProductNotFoundException {
        List<Product> products=productRepository.findByCategoryNameAndBrand(category,brand).orElseThrow(()->new ProductNotFoundException("Given product not found"));
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
    public List<Product> getProductsByName(String name) throws EmptyDataException, ProductNotFoundException {
        List<Product> products=productRepository.findByName(name).orElseThrow(()->new ProductNotFoundException("Given product not found"));
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
    public List<Product> getProductsByBrandAndName(String category, String name) throws EmptyDataException, ProductNotFoundException {
        List<Product> products=productRepository.findByBrandAndName(category,name).orElseThrow(()->new ProductNotFoundException("Given product not found"));
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
