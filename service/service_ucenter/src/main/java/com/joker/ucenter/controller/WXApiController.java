package com.joker.ucenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.joker.commonutils.util.JwtUtils;
import com.joker.servicebase.exception.GuliException;
import com.joker.ucenter.entity.UcenterMember;
import com.joker.ucenter.service.UcenterMemberService;
import com.joker.ucenter.utils.ConstantWXUtils;
import com.joker.ucenter.utils.HttpClientUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@CrossOrigin
@Controller
@Api(tags = "微信扫码登陆")
@RequestMapping("/api/ucenter/wx")
public class WXApiController {


  @Autowired private UcenterMemberService memberService;
  @GetMapping("login")
  public String getWxQrCode() {
    // 微信开放平台授权baseUrl
    String baseUrl =
        "https://open.weixin.qq.com/connect/qrconnect"
            + "?appid=%s"
            + "&redirect_uri=%s"
            + "&response_type=code"
            + "&scope=snsapi_login"
            + "&state=%s"
            + "#wechat_redirect";

    String redirectUrl = ConstantWXUtils.WX_OPEN_REDIRECT_URL;
    try {
      redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    // 防止csrf攻击（跨站请求伪造攻击）
    String state = UUID.randomUUID().toString().replaceAll("-", ""); // 一般情况下会使用一个随机数
    // String state = "imhelen";//为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
    System.out.println("state = " + state);

    String qrcodeUrl = String.format(baseUrl, ConstantWXUtils.WX_OPEN_APP_ID, redirectUrl, state);

    return "redirect:" + qrcodeUrl;
  }

  @GetMapping("callback")
  public String callback(String code, String state) {
    // 得到授权临时票据code
    System.out.println(code);
    System.out.println(state);
    // 从redis中将state获取出来，和当前传入的state作比较
    // 如果一致则放行，如果不一致则抛出异常：非法访问
    // 向认证服务器发送请求换取access_token
    String baseAccessTokenUrl =
        "https://api.weixin.qq.com/sns/oauth2/access_token"
            + "?appid=%s"
            + "&secret=%s"
            + "&code=%s"
            + "&grant_type=authorization_code";
    String accessTokenUrl =
        String.format(
            baseAccessTokenUrl,
            ConstantWXUtils.WX_OPEN_APP_ID,
            ConstantWXUtils.WX_OPEN_APP_SECRET,
            code);

    String result = null;
    try {
      result = HttpClientUtils.get(accessTokenUrl);
    } catch (Exception e) {
      e.printStackTrace();
    }
    // 解析json字符串
    Gson gson = new Gson();
    HashMap map = gson.fromJson(result, HashMap.class);
    String accessToken = (String) map.get("access_token");
    String openid = (String) map.get("openid");



    UcenterMember member=memberService.getByOpenid(openid);
    if (null == member) {

      //访问微信的资源服务器，获取用户信息
      String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
              "?access_token=%s" +
              "&openid=%s";
      String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);

      String resultUserInfo = null;
      try {
        resultUserInfo = HttpClientUtils.get(userInfoUrl);
        System.out.println("resultUserInfo==========" + resultUserInfo);
      } catch (Exception e) {
        throw new GuliException(20001, "获取用户信息失败");
      }
//解析json
      HashMap<String, Object> mapUserInfo = gson.fromJson(resultUserInfo, HashMap.class);
      System.out.println("转换后："+mapUserInfo);
      String nickname = mapUserInfo.get("nickname").toString();
      String headimgurl = mapUserInfo.get("headimgurl").toString();

      //向数据库中插入一条记录
      member=new UcenterMember();
      member.setNickname(nickname);
      member.setOpenid(openid);
      member.setAvatar(headimgurl);
      member.setGmtCreate(new Date());
      member.setGmtModified(new Date());
      memberService.save(member);
    }

    String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());

    return "redirect:http://localhost:3000?token="+token;
  }
}
