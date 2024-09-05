package com.kumar.shopperstop.Repository.Product;


import com.kumar.shopperstop.Model.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<List<Product>> findByCategoryName(String category);

    Optional<List<Product>> findByBrand(String brand);

    Optional<List<Product>> findByCategoryNameAndBrand(String category, String brand);

    Optional<List<Product>> findByName(String name);

    Optional<List<Product>> findByBrandAndName(String brand, String name);

    Long countByBrandAndName(String brand ,String name);

    boolean existsByNameAndBrand(String name, String brand);
}
