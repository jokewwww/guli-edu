package com.joker.statistics.client;


import com.joker.commonutils.response.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-educenter")
public interface UcenterClient {

    @GetMapping("/educenter/member/countregister/{day}")
    public R registerCount(@PathVariable("day") String day);


}
