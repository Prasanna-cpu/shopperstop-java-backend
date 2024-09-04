package com.kumar.shopperstop.Repository.Order;

import com.kumar.shopperstop.Model.Cart.Cart;
import com.kumar.shopperstop.Model.Order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<List<Order>> findByUserId(Long userId);

}
