package com.systemlab.ecommerce_application.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    private BigDecimal price;
    private Integer stockQuantity;
    private String category;
    private String imageUrl;
    private Boolean active;

}
