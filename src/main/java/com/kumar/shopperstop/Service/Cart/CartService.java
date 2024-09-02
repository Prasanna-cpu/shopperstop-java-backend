package com.kumar.shopperstop.Service.Cart;

import com.kumar.shopperstop.Exceptions.CartNotFoundException;
import com.kumar.shopperstop.Model.Cart.Cart;

import java.math.BigDecimal;

public interface CartService {

    Cart getCart(Long id) throws CartNotFoundException;

    void clearCart(Long id) throws CartNotFoundException;

    BigDecimal getTotalPrice(Long id) throws CartNotFoundException;


    Long initializeNewCart();
}
