package com.joker.ucenter.service;

import com.joker.ucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joker.ucenter.entity.vo.RegistryVO;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author joker
 * @since 2020-09-14
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember ucenterMember);

    void register(RegistryVO registryVO);

    UcenterMember getByOpenid(String openId);

    Integer countRegisterByDay(String day);
}
