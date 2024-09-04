package com.kumar.shopperstop.Service.Order;

import com.kumar.shopperstop.DTO.OrderDTO;
import com.kumar.shopperstop.Exceptions.CartNotFoundException;
import com.kumar.shopperstop.Exceptions.OrderNotFoundException;
import com.kumar.shopperstop.Exceptions.UserNotFoundException;
import com.kumar.shopperstop.Model.Order.Order;

import java.util.List;

public interface OrderService {


    OrderDTO convertToOrderDTO(Order order);

    List<OrderDTO> getConvertedOrders(List<Order> orders);

    Order placeOrder(Long userId) throws CartNotFoundException, UserNotFoundException;

    Order getOrder(Long orderId) throws OrderNotFoundException;

    List<Order> getUserOrders(Long userId) throws UserNotFoundException;
}
