package com.joker.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joker.edu.entity.CrmBanner;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author joker
 * @since 2020-09-06
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> selectAllBanner();
}
