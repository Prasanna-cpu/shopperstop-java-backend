package com.kumar.shopperstop.Repository.CartItem;

import com.kumar.shopperstop.Model.CartItem.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

//    @Transactional
//    @Modifying
//    @Query("delete from CartItem c where c.cart.id = ?1")
    void deleteAllByCartId(Long id);
}
