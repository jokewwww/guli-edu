package com.joker.edu.client;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Component
@FeignClient("service-order")
public interface OrderClient {

    @ApiOperation("查询改用户是否购买过该课程")
    @PostMapping("/eduorder/order/isBuy/{courseId}/{memberId}")
    public boolean isBuyCourse(
            @PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId);
}
