package com.joker.statistics.controller;

import com.joker.commonutils.response.R;
import com.joker.statistics.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 网站统计日数据 前端控制器
 *
 * @author joker
 * @since 2020-09-19
 */
@CrossOrigin
@RestController
@RequestMapping("/staservice/daily")
public class DailyController {

    @Autowired private DailyService dailyService;

    @GetMapping("getStatByDay/{day}")
    public R getStatisByDay(@PathVariable String day){
         dailyService.createStatisticsByDay(day);
        return R.ok();
    }

    @GetMapping("showChart/{begin}/{end}/{type}")
    public R showChart(@PathVariable String begin,@PathVariable String end,@PathVariable String type){
        Map<String, Object> map = dailyService.getChartData(begin, end, type);
        return R.ok().data(map);
    }
}
