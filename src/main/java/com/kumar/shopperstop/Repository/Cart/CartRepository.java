package com.kumar.shopperstop.Repository.Cart;


import com.kumar.shopperstop.Model.Cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

}
