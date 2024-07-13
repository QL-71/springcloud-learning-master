package com.example.architecturemaster.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class OrderDTO {
    private Long id;

    Set<LineItemDTO> lineItemDTOS;

    private Long customerId;

    private DeliveryAddressDTO deliveryAddressDTO;

    private Double orderTotal;

    private String orderState;

    private Long shopId;

    private String shopName;

    private String shopImg;

    private Float deliveryPrice;
}
