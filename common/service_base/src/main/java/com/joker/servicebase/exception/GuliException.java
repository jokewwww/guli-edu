package com.joker.servicebase.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GuliException extends RuntimeException {

    private int code;
    private String message;

}
