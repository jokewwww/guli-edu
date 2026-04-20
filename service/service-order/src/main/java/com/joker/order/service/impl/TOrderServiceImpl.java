package com.joker.order.service.impl;

import com.joker.commonutils.response.orderVo.CourseWebVoOrder;
import com.joker.commonutils.response.orderVo.UcenterMemberOrder;
import com.joker.order.client.EduClient;
import com.joker.order.client.UcenterClient;
import com.joker.order.entity.TOrder;
import com.joker.order.mapper.TOrderMapper;
import com.joker.order.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joker.order.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author joker
 * @since 2020-09-17
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Autowired private EduClient eduClient;
    @Autowired private UcenterClient ucenterClient;

    @Override
    public String saveOrder(String courseId, String memberId) {
        CourseWebVoOrder course = eduClient.getCourse(courseId);
        UcenterMemberOrder member = ucenterClient.getMember(memberId);

       TOrder order=new TOrder(){{
           setOrderNo(OrderNoUtil.getOrderNo());
           setCourseId(courseId);
           setCourseTitle(course.getTitle());
           setCourseCover(course.getCover());
           setTeacherName("test");
           setTotalFee(course.getPrice());
           setMemberId(memberId);
           setMobile(member.getMobile());
           setNickname(member.getNickname());
           setGmtCreate(new Date());
           setGmtModified(new Date());
           setStatus(0);
           setPayType(1);
       }};
       baseMapper.insert(order);

        return order.getOrderNo();
    }
}
