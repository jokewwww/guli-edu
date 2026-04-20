package com.joker.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joker.edu.entity.CrmBanner;
import com.joker.edu.mapper.CrmBannerMapper;
import com.joker.edu.service.CrmBannerService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author joker
 * @since 2020-09-06
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Cacheable(value = "banner", key = "'selectIndexList'")
    @Override
    public List<CrmBanner> selectAllBanner() {
        QueryWrapper<CrmBanner> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit 2");
        return baseMapper.selectList(queryWrapper);


    }
}
