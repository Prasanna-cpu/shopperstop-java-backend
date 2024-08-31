package com.kumar.shopperstop.Request;

import com.kumar.shopperstop.Model.Category.Category;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class UpdateProductRequest {

    private Long id;

    private String name;

    private String brand;

    private String description;

    private BigDecimal price;

    private Integer inventory;

    private Category category;
}
