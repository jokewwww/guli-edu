package com.joker.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joker.edu.entity.EduTeacher;
import com.joker.edu.mapper.EduTeacherMapper;
import com.joker.edu.service.EduTeacherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author joker
 * @since 2020-08-11
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public Map<String, Object> getTecherPageList(Page teacherPage) {
        QueryWrapper<EduTeacher> queryWrapper=new QueryWrapper();
        queryWrapper.orderByDesc("id");

        baseMapper.selectPage(teacherPage,queryWrapper);
        List<EduTeacher> records = teacherPage.getRecords();
        long current = teacherPage.getCurrent();
        long pages = teacherPage.getPages();
        long size = teacherPage.getSize();
        long total = teacherPage.getTotal();
        boolean hasNext = teacherPage.hasNext();//下一页
        boolean hasPrevious = teacherPage.hasPrevious();//上一页

        Map map = new HashMap() {{
            put("records", records);
            put("current", current);
            put("pages", pages);
            put("size", size);
            put("total", total);
            put("hasNext", hasNext);
            put("hasPrevious", hasPrevious);
        }};
        return map;
    }
}
