package com.joker.edu.controller;

import com.joker.commonutils.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(tags = "登录")
@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin
public class LoginController {

    @PostMapping("login")
    @ApiOperation("登录")
    public R login(){
        return R.ok();
    }

    @GetMapping("info")
    @ApiOperation("获取用户信息")
    public R getInfo(){
        return R.ok().data("name","joker").data("roles","[admin]")
                .data("avatar","https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1662015695,1366795910&fm=26&gp=0.jpg");
    }

}
