package com.example.architecturemaster.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

    private Long id;

    private String userTelephone;

    private String userName;

    private Integer userSex;

    private String userImg; //头像

    private Integer userType;

    private String token;

    private String refreshToken;

}
