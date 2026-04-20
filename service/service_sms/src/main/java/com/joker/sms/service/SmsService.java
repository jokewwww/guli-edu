package com.joker.sms.service;

import java.util.Map;

public interface SmsService {
    boolean send(String phone, String sms_180051135, Map<String, Object> param);
}
