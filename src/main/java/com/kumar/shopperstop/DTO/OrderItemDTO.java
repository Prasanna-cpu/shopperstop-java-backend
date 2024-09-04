package com.kumar.shopperstop.DTO;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class OrderItemDTO {

    private Long productId;

    private int quantity;

    private String productName;

    private BigDecimal price;


}
