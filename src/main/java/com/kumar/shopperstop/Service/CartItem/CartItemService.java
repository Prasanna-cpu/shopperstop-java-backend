package com.kumar.shopperstop.Service.CartItem;

import com.kumar.shopperstop.Exceptions.CartItemNotFoundException;
import com.kumar.shopperstop.Exceptions.CartNotFoundException;
import com.kumar.shopperstop.Exceptions.ProductNotFoundException;
import com.kumar.shopperstop.Model.CartItem.CartItem;

public interface CartItemService {

    CartItem getCartItem(Long cartId, Long productId) throws CartNotFoundException;

    void addCartItem(Long cartId, Long productId, int quantity) throws CartNotFoundException, ProductNotFoundException;

    void removeCartItem(Long cartId,Long productId) throws CartNotFoundException, CartItemNotFoundException;

    void updateItemQuantity(Long cartId,Long productId,int quantity) throws CartNotFoundException;


}
