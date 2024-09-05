package com.kumar.shopperstop.Controller;


import com.kumar.shopperstop.DTO.OrderDTO;
import com.kumar.shopperstop.Exceptions.CartNotFoundException;
import com.kumar.shopperstop.Exceptions.OrderNotFoundException;
import com.kumar.shopperstop.Exceptions.UserNotFoundException;
import com.kumar.shopperstop.Model.Order.Order;
import com.kumar.shopperstop.Repository.Order.OrderRepository;
import com.kumar.shopperstop.Response.ApiResponse;
import com.kumar.shopperstop.Service.Order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;


    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) throws CartNotFoundException, UserNotFoundException {
        Order order=orderService.placeOrder(userId);
        OrderDTO orderDTO=orderService.convertToOrderDTO(order);
        try{
            return ResponseEntity.ok(new ApiResponse("Order created successfully",orderDTO));
        }
        catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Operation failed",e.getMessage()));
        }

    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getOrder(@PathVariable Long orderId) throws  OrderNotFoundException {
        Order order=orderService.getOrder(orderId);
        OrderDTO orderDTO=orderService.convertToOrderDTO(order);
        try{
            return ResponseEntity.ok(new ApiResponse("Order retrieved",orderDTO));
        }
        catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Operation failed",e.getMessage()));
        }

    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) throws UserNotFoundException {
        List<Order> orders=orderService.getUserOrders(userId);

        List<OrderDTO> ordersDTO=orderService.getConvertedOrders(orders);

        try{
            return ResponseEntity.ok(new ApiResponse("Order retrieved for a user",ordersDTO));
        }
        catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Operation failed",e.getMessage()));
        }
    }






}
