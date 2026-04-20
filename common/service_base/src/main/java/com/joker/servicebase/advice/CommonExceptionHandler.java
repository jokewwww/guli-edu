package com.joker.servicebase.advice;

import com.joker.commonutils.response.R;
import com.joker.servicebase.exception.GuliException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CommonExceptionHandler {

  @ExceptionHandler(Exception.class)
  @ResponseBody
  public R handlerException(Exception e) {
    e.printStackTrace();
    return R.error().message("出错了");
  }

  @ExceptionHandler(GuliException.class)
  @ResponseBody
  public R handlerException(GuliException e) {
    e.printStackTrace();
    return R.error().code(e.getCode()).message(e.getMessage());
  }
}
