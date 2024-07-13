package com.example.architecturemaster.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO {
    private Long id;
    private Long shopId;
    private Long customerId;
    private Long shopItemId;
    private String shopItemName;
    private String shopItemImg;
    private Integer quantity;
    private Double shopItemPrice;
}
