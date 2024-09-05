package com.kumar.shopperstop.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;


@Data
public class CartDTO {
    private Long id;
    private BigDecimal totalAmount;
    private Set<CartItemDTO> items;
}
