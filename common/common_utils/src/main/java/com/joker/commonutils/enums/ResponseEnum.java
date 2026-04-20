package com.joker.commonutils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ResponseEnum {

    SUCCESS(20000,"成功"),
    ERROR(20001,"失败"),
  ;
  private int code;
  private String message;
}
