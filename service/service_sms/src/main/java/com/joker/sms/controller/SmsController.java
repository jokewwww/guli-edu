package com.joker.sms.controller;

import com.joker.commonutils.response.R;
import com.joker.sms.service.SmsService;
import com.joker.sms.utils.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/edusms/sms")
@CrossOrigin
public class SmsController {

  @Autowired private SmsService smsService;

  @Autowired private RedisTemplate<String, String> redisTemplate;

  @GetMapping(value = "/send2/{phone}")
  public R code(@PathVariable String phone) {
    String code = redisTemplate.opsForValue().get(phone);
    if (!StringUtils.isEmpty(code)) {
      return R.ok();
    }
    code = RandomUtil.getFourBitRandom();
    Map<String, Object> param = new HashMap<>();
    param.put("code", code);
    boolean isSend = smsService.send(phone, "SMS_180051135", param);
    if (isSend) {
      redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
      return R.ok();
    } else {
      return R.error().message("发送短信失败");
    }
  }

  @GetMapping(value = "/send/{phone}")
  public R getCode(@PathVariable String phone) {
    String code = redisTemplate.opsForValue().get(phone);
    if (!StringUtils.isEmpty(code)) {
      return R.ok();
    }
    code = "123456";
    redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
    return R.ok();
  }
}
