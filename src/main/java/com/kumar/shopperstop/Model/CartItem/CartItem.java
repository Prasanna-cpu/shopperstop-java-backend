package com.kumar.shopperstop.Model.CartItem;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kumar.shopperstop.DTO.ProductDTO;
import com.kumar.shopperstop.Model.Cart.Cart;
import com.kumar.shopperstop.Model.Product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private Cart cart;


    public void setTotalPrice(){
        this.totalPrice = this.unitPrice.multiply(BigDecimal.valueOf(quantity));
    }




}
