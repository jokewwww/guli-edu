package com.joker.commonutils.response;

import com.joker.commonutils.enums.ExceptionEnum;
import lombok.Data;

@Data
public class ExceptionResult {

    private int code;
    private String message;
    private Long timestamp;

    public ExceptionResult(ExceptionEnum exceptionEnum){
        this.code=exceptionEnum.getCode();
        this.message=exceptionEnum.getMessage();
        this.timestamp=System.currentTimeMillis();
    }
}
