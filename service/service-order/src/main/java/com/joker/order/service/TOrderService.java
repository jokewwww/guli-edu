package com.joker.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joker.order.entity.TOrder;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author joker
 * @since 2020-09-17
 */
public interface TOrderService extends IService<TOrder> {

    String saveOrder(String courseId, String userId);
}
