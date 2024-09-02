package com.kumar.shopperstop.DTO;

import com.kumar.shopperstop.Model.Category.Category;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
public class ProductDTO {

    private Long id;

    private String name;

    private String brand;

    private String description;

    private BigDecimal price;

    private Integer inventory;

    private Category category;

    private List<ImageDTO> images;



}
