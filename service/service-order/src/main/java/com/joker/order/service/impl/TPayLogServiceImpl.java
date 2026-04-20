package com.joker.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.joker.order.entity.TOrder;
import com.joker.order.entity.TPayLog;
import com.joker.order.mapper.TPayLogMapper;
import com.joker.order.service.TOrderService;
import com.joker.order.service.TPayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joker.order.utils.HttpClient;
import com.joker.servicebase.exception.GuliException;
import javafx.scene.control.TabPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author joker
 * @since 2020-09-17
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TPayLogServiceImpl extends ServiceImpl<TPayLogMapper, TPayLog> implements TPayLogService {

    @Autowired private TOrderService orderService;

    @Override
    public Map createNative(String orderNo) {
        try{
            QueryWrapper<TOrder> queryWrapper=new QueryWrapper();
            queryWrapper.eq("order_no",orderNo);
            TOrder order = orderService.getOne(queryWrapper);

            Map m = new HashMap();
            //1、设置支付参数
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            m.put("body", order.getCourseTitle());
            m.put("out_trade_no", orderNo);
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
            m.put("spbill_create_ip", "127.0.0.1");
            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify");
            m.put("trade_type", "NATIVE");

            //2、HTTPClient来根据URL访问第三方接口并且传递参数
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //client设置参数

            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();

            //3、返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //4、封装返回结果集
            Map map = new HashMap<>();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            map.put("result_code", resultMap.get("result_code"));//返回状态码
            map.put("code_url", resultMap.get("code_url"));//二维码地址
            //微信支付二维码2小时过期，可采取2小时未支付取消订单
            //redisTemplate.opsForValue().set(orderNo, map, 120, TimeUnit.MINUTES);
            return map;
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"生成二维码失败");
        }



    }

    /**
     * 根据订单号查询订单状态
     * @param orderNo
     * @return
     */
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try{
            //1.封装参数
            Map map=new HashMap(){{
               put("appid","wx74862e0dfcf69954");
               put("mch_id", "1558950191");
               put("out_trade_no", orderNo);
               put("nonce_str", WXPayUtil.generateNonceStr());
            }};
            //2.设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(map, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();

            //3.返回第三方数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            return resultMap;
        }catch (Exception e){

        }


        return null;
    }

    /**
     * 添加支付记录 更新订单状态
     * @param map
     */
    @Override
    public void updateOrderStatus(Map<String, String> map) {
        String orderNo = map.get("out_trade_no");
        QueryWrapper<TOrder> queryWrapper=new QueryWrapper();
        queryWrapper.eq("order_no",orderNo);
        TOrder order = orderService.getOne(queryWrapper);

        if(order.getStatus().intValue()==1){
            return;
        }
        order.setStatus(1);
        orderService.updateById(order);

        //插入倒支付日志表
        TPayLog payLog=new TPayLog(){{
            setOrderNo(order.getOrderNo());
            setPayTime(new Date());
            setPayType(1);//微信支付
            setTotalFee(order.getTotalFee());
            setTradeState(map.get("trade_state"));//支付状态
            setTransactionId(map.get("transaction_id"));
            setAttr(JSONObject.toJSONString(map));
        }};
        baseMapper.insert(payLog);
    }
}
