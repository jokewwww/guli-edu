package com.joker.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joker.commonutils.response.R;
import com.joker.statistics.client.UcenterClient;
import com.joker.statistics.entity.Daily;
import com.joker.statistics.mapper.DailyMapper;
import com.joker.statistics.service.DailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author joker
 * @since 2020-09-19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired private UcenterClient ucenterClient;

    @Override
    public void createStatisticsByDay(String day) {
        //删除已存在的统计对象
        QueryWrapper<Daily> queryWrapper=new QueryWrapper();
        queryWrapper.eq("date_calculated", day);
        baseMapper.delete(queryWrapper);

        //获取统计信息
        R registerCount = ucenterClient.registerCount(day);
        Integer countRegister = (Integer) registerCount.getData().get("countRegister");
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO

        //创建统计对象
        Daily daily = new Daily() {{
            setRegisterNum(countRegister);
            setLoginNum(loginNum);
            setVideoViewNum(videoViewNum);
            setCourseNum(courseNum);
            setDateCalculated(day);
        }};
        baseMapper.insert(daily);
    }

    @Override
    public Map<String, Object> getChartData(String begin, String end, String type) {
        QueryWrapper<Daily> queryWrapper=new QueryWrapper();
        queryWrapper.select(type,"date_calculated")
                .between("date_calculated",begin,end);
        List<Daily> dailies = baseMapper.selectList(queryWrapper);

        List<String> dateList = new ArrayList();
        List<Integer> numDataList = new ArrayList();

        dailies.stream().forEach(daily -> {
            String dateCalculated = daily.getDateCalculated();
            dateList.add(dateCalculated);

            switch (type){
                case "register_num":
                    numDataList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    numDataList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    numDataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(daily.getCourseNum());
                    break;
                    default:
                        break;
            }

        });

        Map<String, Object> map = new HashMap<>();
        map.put("dateList",dateList);
        map.put("dataList",numDataList);
        return map;
    }
}
