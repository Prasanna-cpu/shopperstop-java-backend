package com.kumar.shopperstop.Repository.OrderItem;

import com.kumar.shopperstop.Model.OrderItem.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

}
