package com.example.architecturemaster.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ShopDTO {
    private Long id;

    private String shopName;

    private String shopImg;

    private String shopTelephone;

    private Float startPrice;

    private Float deliveryPrice;

    private String shopAddress;

    private String shopExplain;

}
