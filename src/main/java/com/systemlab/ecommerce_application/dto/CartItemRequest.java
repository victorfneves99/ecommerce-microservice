package com.systemlab.ecommerce_application.dto;

import lombok.Data;

@Data
public class CartItemRequest {

    private Long productId;
    private Integer quantity;

}
