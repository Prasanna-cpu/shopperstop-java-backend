package com.kumar.shopperstop.Controller;


import com.kumar.shopperstop.DTO.CartDTO;
import com.kumar.shopperstop.Exceptions.CartItemNotFoundException;
import com.kumar.shopperstop.Exceptions.CartNotFoundException;
import com.kumar.shopperstop.Exceptions.ProductNotFoundException;
import com.kumar.shopperstop.Exceptions.UserNotFoundException;
import com.kumar.shopperstop.Model.Cart.Cart;
import com.kumar.shopperstop.Model.User.User;
import com.kumar.shopperstop.Response.ApiResponse;
import com.kumar.shopperstop.Service.Cart.CartService;
import com.kumar.shopperstop.Service.CartItem.CartItemService;
import com.kumar.shopperstop.Service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {

    private final CartItemService cartItemService;
    private final UserService userService;
    private final CartService cartService;


    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(
//            @RequestParam(required = false) Long cartId,
            @RequestParam Long productId,
            @RequestParam int quantity
    ) throws ProductNotFoundException, CartNotFoundException, UserNotFoundException {

        User user=userService.getUserById(1L);
        Cart cart=cartService.initializeNewCart(user);

        cartItemService.addCartItem(cart.getId(),productId,quantity);

        try{
            return ResponseEntity.ok(
                    new ApiResponse("Item added to cart successfully",null)
            );
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Failed to add item to cart",e.getMessage()));
        }

    }

    @DeleteMapping("/cart/{cartId}/item/{productId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(
            @PathVariable Long cartId,
            @PathVariable Long productId
    ) throws  CartNotFoundException, CartItemNotFoundException {
        cartItemService.removeCartItem(cartId,productId);

        try{
            return ResponseEntity.ok(
                    new ApiResponse("Item removed from cart successfully",null)
            );
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Failed to remove item from cart",e.getMessage()));
        }

    }


    @PutMapping("/cart/{cartId}/item/{productId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(
            @PathVariable Long cartId,
            @PathVariable Long productId,
            @RequestParam int quantity
    ) throws CartNotFoundException {
        cartItemService.updateItemQuantity(cartId,productId,quantity);
        try{
            return ResponseEntity.ok(
                    new ApiResponse("Item quantity changed",null)
            );
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Failed to remove item from cart",e.getMessage()));
        }
    }


}
