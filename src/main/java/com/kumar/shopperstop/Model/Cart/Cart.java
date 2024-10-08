package com.kumar.shopperstop.Model.Cart;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kumar.shopperstop.Model.CartItem.CartItem;
import com.kumar.shopperstop.Model.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal totalAmount=BigDecimal.ZERO;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private Set<CartItem> items=new HashSet<>();


    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    private void updateAmount(){
        this.totalAmount=items.stream().map(item->{
            BigDecimal unitPrice=item.getProduct().getPrice();
            if(unitPrice==null){
                return BigDecimal.ZERO;
            }
            return unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    public void addItem(CartItem item) {
        this.items.add(item);
        item.setCart(this);
        updateAmount();
    }

    public void removeItem(CartItem item) {
        this.items.remove(item);
        item.setCart(null);
        updateAmount();
    }





}
