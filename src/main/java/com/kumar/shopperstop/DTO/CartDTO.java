package com.kumar.shopperstop.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
public class CartDTO {
    private Long id;
    private BigDecimal totalAmount;
    private List<CartItemDTO> items;
}
