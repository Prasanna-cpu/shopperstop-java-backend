package com.kumar.shopperstop.Service.Cart;


import com.kumar.shopperstop.Exceptions.CartNotFoundException;
import com.kumar.shopperstop.Model.Cart.Cart;
import com.kumar.shopperstop.Repository.Cart.CartRepository;
import com.kumar.shopperstop.Repository.CartItem.CartItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class CartServiceImplementation implements CartService {


    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final AtomicLong cartIdGenerator=new AtomicLong(0);

    @Override
    public Cart getCart(Long id) throws CartNotFoundException {
        Cart cart=cartRepository.findById(id).orElseThrow(()->new CartNotFoundException("Cart not found"));

        BigDecimal totalAmount=cart.getTotalAmount();

        cart.setTotalAmount(totalAmount);

        Cart savedCart=cartRepository.save(cart);

        return savedCart;
    }

    @Override
    public void clearCart(Long id) throws CartNotFoundException {

        Cart cart=getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cartRepository.deleteById(id);


    }

    @Override
    public BigDecimal getTotalPrice(Long id) throws CartNotFoundException {
        Cart cart=getCart(id);

        BigDecimal totalPrice=cart.
                getTotalAmount();

        return totalPrice;
    }

    @Override
    public Long initializeNewCart(){
        Cart newCart=new Cart();
        Long newCartId=cartIdGenerator.incrementAndGet();
        newCart.setId(newCartId);
        return cartRepository.save(newCart).getId();
    }



}
