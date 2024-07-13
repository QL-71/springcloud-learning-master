package com.example.architecturemaster.VO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ResponseVO<T> implements Serializable {

    private Integer code;

    private String msg;

    private T data;

    public ResponseVO() {

    }

    public ResponseVO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseVO(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseVO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ResponseVO(ResponseStatusEnum resultStatus, T data) {
        this.code = resultStatus.getCode();
        this.msg = resultStatus.getMsg();
        this.data = data;
    }


    public static <T> ResponseVO<T> success(T data) {
        return new ResponseVO<T>(ResponseStatusEnum.SUCCESS, data);
    }


    public static <T> ResponseVO<T> failure(ResponseStatusEnum resultStatus, T data) {
        if (resultStatus == null) {
            return new ResponseVO<T>(ResponseStatusEnum.SYSTEM_ERROR, null);
        }
        return new ResponseVO<T>(resultStatus, data);
    }

    public static <T> ResponseVO<T> failure() {
        return new ResponseVO<T>(ResponseStatusEnum.SYSTEM_ERROR, null);
    }


    @Override
    public String toString() {
        return "ResponseVO{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + (data != null ? data.toString() : "null") +
                '}';
    }
}



