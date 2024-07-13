package com.example.architecturemaster.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DeliveryAddressDTO {
    private Long id;

    private String contactName;

    private Integer contactSex;

    private String contactTel;

    private String address;

    private Long customerId;

}
