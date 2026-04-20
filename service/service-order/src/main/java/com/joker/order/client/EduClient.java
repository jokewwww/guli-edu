package com.joker.order.client;


import com.joker.commonutils.response.orderVo.CourseWebVoOrder;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("server-edu")
public interface EduClient {

    @ApiOperation("根据Id获取课程信息")
    @PostMapping("/eduservice/coursefront/getCourseOrder/{id}")
    public CourseWebVoOrder getCourse(@PathVariable("id") String id);
}
