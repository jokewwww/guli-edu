package com.joker.ucenter.controller;


import com.joker.commonutils.response.R;
import com.joker.commonutils.response.orderVo.UcenterMemberOrder;
import com.joker.commonutils.util.JwtUtils;
import com.joker.ucenter.entity.UcenterMember;
import com.joker.ucenter.entity.vo.RegistryVO;
import com.joker.ucenter.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author joker
 * @since 2020-09-14
 */
@CrossOrigin
@RestController
@Api(tags = "登录注册接口")
@RequestMapping("/educenter/member")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    //登录
    @ApiOperation("登录")
    @PostMapping("login")
    public R login(@RequestBody UcenterMember ucenterMember){
        String token=memberService.login(ucenterMember);
        return R.ok().data("token",token);
    }

    //注册
    @ApiOperation("注册")
    @PostMapping("register")
    public R register(@RequestBody RegistryVO registryVO){
        memberService.register(registryVO);
        return R.ok();
    }

    @ApiOperation("根据token获取用户信息")
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        String tokenId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = memberService.getById(tokenId);

        return R.ok().data("userInfo",member);
    }

    @ApiOperation("根据id获取用户信息")
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getMember(@PathVariable("id") String id){
        UcenterMember member = memberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    @GetMapping("countregister/{day}")
    public R registerCount(@PathVariable String day) {
        Integer count = memberService.countRegisterByDay(day);
        return R.ok().data("countRegister", count);
    }

}

