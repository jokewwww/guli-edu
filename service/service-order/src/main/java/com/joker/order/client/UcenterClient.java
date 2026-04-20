package com.joker.order.client;


import com.joker.commonutils.response.orderVo.UcenterMemberOrder;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-educenter")
public interface UcenterClient {

    @ApiOperation("根据id获取用户信息")
    @PostMapping("/educenter/member/getUserInfoOrder/{id}")
    public UcenterMemberOrder getMember(@PathVariable("id") String id);

}
