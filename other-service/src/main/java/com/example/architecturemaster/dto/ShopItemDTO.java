package com.example.architecturemaster.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopItemDTO {
    private Long id;

    private Long shopId;

    private String shopName;

    private Long itemId;

    private String itemName;

    private Double shopItemPrice;

    private Boolean state;

    private String itemImg;

    private String itemRemark;
}
