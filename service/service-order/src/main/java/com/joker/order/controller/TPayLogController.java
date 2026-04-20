package com.joker.order.controller;


import com.joker.commonutils.response.R;
import com.joker.order.service.TPayLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author joker
 * @since 2020-09-17
 */
@RestController
@Api(tags = "支付记录接口")
@RequestMapping("/eduorder/payLog")
@CrossOrigin
public class TPayLogController {

    @Autowired private TPayLogService payLogService;

    @ApiOperation("生成微信支付二维码")
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        //返回信息，包含二维码地址，还有其他信息
        Map map=payLogService.createNative(orderNo);
        return R.ok().data(map);
    }

    @ApiOperation("查询订单状态")
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){

        Map<String,String> map=payLogService.queryPayStatus(orderNo);
        if(null==map){
            return R.error().message("支付出错");
        }
        System.out.println("+++支付状态信息+++"+map);
        if(map.get("trade_state").equals("SUCCESS")){
            payLogService.updateOrderStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");
    }

}

