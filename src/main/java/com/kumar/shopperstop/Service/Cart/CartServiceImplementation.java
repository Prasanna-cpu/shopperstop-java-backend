package com.kumar.shopperstop.Service.Cart;


import com.kumar.shopperstop.DTO.CartDTO;
import com.kumar.shopperstop.DTO.CartItemDTO;
import com.kumar.shopperstop.DTO.ProductDTO;
import com.kumar.shopperstop.Exceptions.CartNotFoundException;
import com.kumar.shopperstop.Model.Cart.Cart;
import com.kumar.shopperstop.Model.CartItem.CartItem;
import com.kumar.shopperstop.Repository.Cart.CartRepository;
import com.kumar.shopperstop.Repository.CartItem.CartItemRepository;
import com.kumar.shopperstop.Service.Product.ProductService;
//import jakarta.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@Slf4j
@jakarta.transaction.Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class CartServiceImplementation implements CartService {


    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final ProductService productService;

    private final AtomicLong cartIdGenerator=new AtomicLong(0);
    private final ModelMapper modelMapper;


    private CartItemDTO mapToCartItemDTO(CartItem cartItem){
        CartItemDTO cartItemDTO=modelMapper.map(cartItem,CartItemDTO.class);
        ProductDTO productDTO=productService.mapToProductDTO(cartItem.getProduct());

        cartItemDTO.setProduct(productDTO);

        return cartItemDTO;

    }

    @Override
    public CartDTO mapToCartDTO(Cart cart){
        CartDTO cartDTO=modelMapper.map(cart,CartDTO.class);
        List<CartItemDTO> cartItemDTOList=cart.getItems().stream().map(this::mapToCartItemDTO).collect(Collectors.toList());
        cartDTO.setItems(cartItemDTOList);
        return cartDTO;
    }


    @Transactional(readOnly=true)
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
