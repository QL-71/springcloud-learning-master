package com.example.architecturemaster.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ItemDTO {
    private Long id;

    private Long businessId;

    private String itemName;

    private String itemImg;

    private String itemRemark;
}
