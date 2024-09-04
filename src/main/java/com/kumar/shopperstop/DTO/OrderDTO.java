package com.kumar.shopperstop.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class OrderDTO {

    private Long id;

    private Long userId;

    private LocalDate orderDate;

    private BigDecimal totalAmount;

    private String status;

    private List<OrderItemDTO> orderItems;
}
