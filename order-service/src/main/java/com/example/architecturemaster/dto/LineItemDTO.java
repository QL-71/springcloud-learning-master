package com.example.architecturemaster.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LineItemDTO {

    private Long id;

    private Long shopItemId;

    private Double shopItemPrice;

    private String itemName;

    private String itemImg;

    private Integer quantity;

}
