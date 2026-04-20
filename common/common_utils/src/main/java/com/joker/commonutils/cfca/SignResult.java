package com.joker.commonutils.cfca;


import lombok.Data;

@Data
public class SignResult {

    /**
     * 返回码
     */
    private int code;
    /**
     * 返回描述
     */
    private String message;
    /**
     * 签名加密
     */
    private String sign;


}
