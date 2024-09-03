package com.kumar.shopperstop.Controller;


import com.kumar.shopperstop.DTO.CartDTO;
import com.kumar.shopperstop.Exceptions.CartNotFoundException;
import com.kumar.shopperstop.Model.Cart.Cart;
import com.kumar.shopperstop.Response.ApiResponse;
import com.kumar.shopperstop.Service.Cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/carts")
public class CartController {

    private final CartService cartService;

    @RequestMapping("/cart/{cartId}")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId) throws CartNotFoundException {
        Cart cart=cartService.getCart(cartId);
        CartDTO cartDTO=cartService.mapToCartDTO(cart);

        try{
            return ResponseEntity.ok(
                    new ApiResponse("Cart found",cartDTO)
            );
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Operation failed",e.getMessage()));
        }

    }

    @DeleteMapping("/cart/{id}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long id) throws CartNotFoundException {
        cartService.clearCart(id);

        try{
            return ResponseEntity.ok(
                    new ApiResponse("Cart cleared",null)
            );
        }
        catch (Exception e){
            return ResponseEntity.
                    status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new ApiResponse("Operation failed",e.getMessage()));
        }
    }

    @GetMapping("/cart/{cartId}/total")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cartId) throws CartNotFoundException {
        BigDecimal totalPrice=cartService.getTotalPrice(cartId);


        try {
            return ResponseEntity.ok(
                    new ApiResponse("Total amount",totalPrice)
            );
        }
        catch(Exception e){
            return ResponseEntity.
                    status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new ApiResponse("Operation failed",e.getMessage()));
        }

    }


}
