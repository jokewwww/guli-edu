package com.joker.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joker.commonutils.response.R;
import com.joker.edu.entity.EduCourse;
import com.joker.edu.entity.EduTeacher;
import com.joker.edu.service.EduCourseService;
import com.joker.edu.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/indexfront")
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;

    @GetMapping("/index")
    public R index(){
        //8条热门课程
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit 8");
        List<EduCourse> list = courseService.list(queryWrapper);

        //4条热门讲师
        QueryWrapper<EduTeacher> wrapper=new QueryWrapper();
        wrapper.orderByDesc("id");
        wrapper.last("limit 4");
        List<EduTeacher> teachers = teacherService.list(wrapper);
        return R.ok().data("courseList",list).data("teacherList",teachers);
    }
}
