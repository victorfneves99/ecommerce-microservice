package com.systemlab.ecommerce_application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


import com.systemlab.ecommerce_application.model.OderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private BigDecimal totalAmount;
    private OderStatus status;
    private List<OrderItemDTO> items;
    private LocalDateTime createdAt;

}
