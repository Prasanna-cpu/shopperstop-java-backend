package com.kumar.shopperstop.Service.Cart;

import com.kumar.shopperstop.DTO.CartDTO;
import com.kumar.shopperstop.Exceptions.CartNotFoundException;
import com.kumar.shopperstop.Exceptions.UserNotFoundException;
import com.kumar.shopperstop.Model.Cart.Cart;
import com.kumar.shopperstop.Model.User.User;

import java.math.BigDecimal;

public interface CartService {

    CartDTO mapToCartDTO(Cart cart);

    Cart getCart(Long id) throws CartNotFoundException;

    void clearCart(Long id) throws CartNotFoundException;

    BigDecimal getTotalPrice(Long id) throws CartNotFoundException;

    Cart getCartByUserId(Long userId) throws CartNotFoundException, UserNotFoundException;


    Cart initializeNewCart(User user) throws CartNotFoundException, UserNotFoundException;
}
