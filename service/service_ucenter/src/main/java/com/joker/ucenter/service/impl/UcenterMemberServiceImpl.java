package com.joker.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joker.commonutils.util.JwtUtils;
import com.joker.servicebase.exception.GuliException;
import com.joker.ucenter.entity.UcenterMember;
import com.joker.ucenter.entity.vo.RegistryVO;
import com.joker.ucenter.mapper.UcenterMemberMapper;
import com.joker.ucenter.service.UcenterMemberService;
import com.joker.ucenter.utils.MD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author joker
 * @since 2020-09-14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String login(UcenterMember ucenterMember) {
        String mobile = ucenterMember.getMobile();
        String password = ucenterMember.getPassword();
        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            throw new GuliException(20001,"参数错误");
        }

        QueryWrapper<UcenterMember> queryWrapper=new QueryWrapper();
            queryWrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(queryWrapper);
        if(null==mobileMember){
            throw new GuliException(20001,"手机号不正确");
        }
        if(!MD5.encrypt(password).equals(mobileMember.getPassword())){
            throw new GuliException(20001,"密码错误");
        }
        if(mobileMember.getIsDisabled()){
            throw new GuliException(20001,"该用户已被禁用");
        }

        //登录成功
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return jwtToken;
    }

    @Override
    public void register(RegistryVO registryVO) {
        String code = registryVO.getCode();
        String mobile = registryVO.getMobile();
        String nickname = registryVO.getNickname();
        String password = registryVO.getPassword();
        if(StringUtils.isEmpty(code)
                ||StringUtils.isEmpty(mobile)
                ||StringUtils.isEmpty(nickname)
                ||StringUtils.isEmpty(password)){
            throw new GuliException(20001,"参数不正确");
        }
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper();
        queryWrapper.eq("mobile",mobile);
        Integer integer = baseMapper.selectCount(queryWrapper);
        if(integer>0){
            throw new GuliException(20001,"改手机号已被注册");
        }
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(StringUtils.isBlank(redisCode)){
            throw new GuliException(20001,"验证码已过期");
        }
        if(!code.equals(redisCode)){
            throw new GuliException(20001,"验证码不正确");
        }
        UcenterMember ucenterMember = new UcenterMember() {{
            setIsDisabled(false);
            setMobile(mobile);
            setNickname(nickname);
            setPassword(MD5.encrypt(password));
            setAvatar("https://avatar.csdnimg.cn/B/3/D/1_weixin_44264130.jpg");
        }};

        this.save(ucenterMember);


    }

    @Override
    public UcenterMember getByOpenid(String openId) {
        QueryWrapper<UcenterMember> queryWrapper=new QueryWrapper();
        queryWrapper.eq("openid",openId);
        UcenterMember member = baseMapper.selectOne(queryWrapper);
        return member;
    }

    @Override
    public Integer countRegisterByDay(String day) {
        return baseMapper.selectRegisterCount(day);
    }
}
