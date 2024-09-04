package com.kumar.shopperstop.Service.Order;


import com.kumar.shopperstop.DTO.OrderDTO;
import com.kumar.shopperstop.Exceptions.CartNotFoundException;
import com.kumar.shopperstop.Exceptions.OrderNotFoundException;
import com.kumar.shopperstop.Exceptions.UserNotFoundException;
import com.kumar.shopperstop.Model.Cart.Cart;
import com.kumar.shopperstop.Model.Order.Order;
import com.kumar.shopperstop.Model.OrderItem.OrderItem;
import com.kumar.shopperstop.Model.Product.Product;
import com.kumar.shopperstop.Model.User.User;
import com.kumar.shopperstop.Repository.Order.OrderRepository;
import com.kumar.shopperstop.Repository.Product.ProductRepository;
import com.kumar.shopperstop.Service.Cart.CartService;
import com.kumar.shopperstop.Service.User.UserService;
import com.kumar.shopperstop.Utils.Enums.OrderStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class OrderServiceImplementation implements OrderService{
    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    private final CartService cartService;

    private final UserService userService;

    private final ModelMapper modelMapper;


    @Override
    public OrderDTO convertToOrderDTO(Order order){
        return modelMapper.map(order,OrderDTO.class);
    }

    @Override
    public List<OrderDTO> getConvertedOrders(List<Order> orders){
        return orders
                .stream()
                .map(this::convertToOrderDTO)
                .collect(Collectors.toList());
    }



    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemsList) {
        return orderItemsList
                .stream()
                .map(item->item.getPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart){
        List<OrderItem> items=cart.getItems()
                .stream()
                .map(cartItem->{
                    Product product=cartItem.getProduct();
                    product.setInventory(product.getInventory()-cartItem.getQuantity());
                    productRepository.save(product);
                    return new OrderItem(
                            order,
                            product,
                            cartItem.getQuantity(),
                            cartItem.getUnitPrice()
                    );
                }).toList();

        return items;
    }

    private Order createOrder(Cart cart) {

        Order order=new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;

    }


    @Override
    public Order placeOrder(Long userId) throws CartNotFoundException, UserNotFoundException {

        User user=userService.getUserById(userId);

        Cart cart=cartService.getCartByUserId(userId);
        Order order=createOrder(cart);
        List<OrderItem> orderItemList=createOrderItems(order,cart);

        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));

        Order savedOrder=orderRepository.save(order);

        cartService.clearCart(cart.getId());

        return savedOrder;


    }

    @Override
    public Order getOrder(Long orderId) throws OrderNotFoundException {


        Order order=orderRepository
                .findById(orderId)
                .orElseThrow(()->new OrderNotFoundException("Order not found"));
        return order;
    }

    @Override
    public List<Order> getUserOrders(Long userId) throws UserNotFoundException {
        List<Order> orders=orderRepository.findByUserId(userId).orElseThrow(()->new UserNotFoundException("User not found"));

        if(orders.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        return orders;

    }


}
