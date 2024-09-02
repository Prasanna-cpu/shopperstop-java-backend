package com.kumar.shopperstop.Service.CartItem;


import com.kumar.shopperstop.Exceptions.CartItemNotFoundException;
import com.kumar.shopperstop.Exceptions.CartNotFoundException;
import com.kumar.shopperstop.Exceptions.ProductNotFoundException;
import com.kumar.shopperstop.Model.Cart.Cart;
import com.kumar.shopperstop.Model.CartItem.CartItem;
import com.kumar.shopperstop.Model.Product.Product;
import com.kumar.shopperstop.Repository.Cart.CartRepository;
import com.kumar.shopperstop.Repository.CartItem.CartItemRepository;
import com.kumar.shopperstop.Service.Cart.CartService;
import com.kumar.shopperstop.Service.Product.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class CartItemServiceImplementation implements CartItemService {

    private final CartItemRepository cartItemRepository;

    private final CartService cartService;

    private final ProductService productService;
    private final CartRepository cartRepository;

    @Override
    public CartItem getCartItem(Long cartId,Long productId) throws CartNotFoundException {
        Cart cart=cartService.getCart(cartId);

        return cart.
                getItems().
                stream().
                filter(item->item.getProduct().getId().equals(productId)).
                findFirst().
                orElse(
                new CartItem()
        );
    }



    @Override
    public void addCartItem(Long cartId, Long productId, int quantity) throws CartNotFoundException, ProductNotFoundException {

        //1 . Get the cart
        // 2. Get the product
        // 3. Check if product exist in the cart
        // 4. If yes , increase the quantity
        // 5. If No , initiate a new cart item entry


        Cart cart=cartService.getCart(cartId);

        Product product=productService.getProductById(productId);

        CartItem cartItem=getCartItem(cartId,productId);

        if(cartItem.getId()==null){
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }
        else{
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
        }

        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);



    }

    @Override
    public void removeCartItem(Long cartId, Long productId) throws CartNotFoundException, CartItemNotFoundException {

        Cart cart=cartService.getCart(cartId);

        CartItem cartItem=cart.getItems().
                stream().
                filter(item->item.getProduct().getId().equals(productId)).
                findFirst().
                orElseThrow(()->new CartItemNotFoundException("Item Not Found"));

        cart.removeItem(cartItem);
        cartRepository.save(cart);

    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) throws CartNotFoundException {

        Cart cart=cartService.getCart(cartId);

        cart.getItems().
                stream().
                filter(item->item.getProduct().getId().equals(productId)).
                findFirst().
                ifPresent(item->{
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                });

        BigDecimal totalAmount = cart.getItems()
                .stream().map(CartItem ::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);

    }
}
