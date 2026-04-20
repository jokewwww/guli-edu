package com.joker.ucenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joker.ucenter.entity.UcenterMember;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author joker
 * @since 2020-09-14
 */
@Mapper
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer selectRegisterCount(String day);

}
