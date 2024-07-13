package com.example.architecturemaster.VO;


import lombok.Getter;

@Getter
public enum ResponseStatusEnum {

    SUCCESS(200, "Success"),
    Auth_ERROR(401,"auth error"),
    SYSTEM_ERROR(500, "Internal Server Error"),
    Message_ERROR(501,"Message Error"),
    Pay_ERROR(503,"Pay Error");


    // 可以根据需要添加其他状态

    private final Integer code;
    private final String msg;

    ResponseStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}



