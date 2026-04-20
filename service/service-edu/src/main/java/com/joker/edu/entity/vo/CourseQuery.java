package com.joker.edu.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



@Data
public class CourseQuery {

    @ApiModelProperty(value = "课程名称,模糊查询")
    private String name;
    @ApiModelProperty(value = "状态 Draft未发布 Normal已发布")
    private String status;
    @ApiModelProperty(value = "查询开始时间")
    private String start;
    @ApiModelProperty(value = "查询结束时间")
    private String end;
}
