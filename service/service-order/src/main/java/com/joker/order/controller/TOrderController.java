package com.joker.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joker.commonutils.response.R;
import com.joker.commonutils.util.JwtUtils;
import com.joker.order.entity.TOrder;
import com.joker.order.service.TOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单 前端控制器
 *
 * @author joker
 * @since 2020-09-17
 */
@RestController
@CrossOrigin
@Api(tags = "订单相关接口")
@RequestMapping("/eduorder/order")
public class TOrderController {

  @Autowired private TOrderService orderService;

  @ApiOperation("生成订单")
  @PostMapping("createOrder/{courseId}")
  public R createOrder(@PathVariable String courseId, HttpServletRequest request) {

    String userId = JwtUtils.getMemberIdByJwtToken(request);
    String orderNo = orderService.saveOrder(courseId, userId);
    return R.ok().data("orderId", orderNo);
  }

  @ApiOperation("根据订单号查询订单")
  @PostMapping("getOrder/{orderId}")
  public R getOrder(@PathVariable String orderId) {

    TOrder order = orderService.getOne(new QueryWrapper<TOrder>().eq("order_no", orderId));
    return R.ok().data("order", order);
  }

  @ApiOperation("查询改用户是否购买过该课程")
  @PostMapping("isBuy/{courseId}/{memberId}")
  public boolean isBuyCourse(
      @PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId) {

    QueryWrapper<TOrder> queryWrapper = new QueryWrapper();
    queryWrapper.eq("course_id", courseId).eq("member_id", memberId).eq("status", 1);

    int count = orderService.count(queryWrapper);
    return count > 0;
  }
}
