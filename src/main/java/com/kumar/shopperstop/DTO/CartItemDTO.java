package com.kumar.shopperstop.DTO;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class CartItemDTO {
    private Long id;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private ProductDTO product;
}
