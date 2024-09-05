package com.kumar.shopperstop.Model.Order;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kumar.shopperstop.Model.OrderItem.OrderItem;
import com.kumar.shopperstop.Model.User.User;
import com.kumar.shopperstop.Utils.Enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.aspectj.runtime.reflect.Factory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private LocalDate orderDate;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<OrderItem> orderItems;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


}
