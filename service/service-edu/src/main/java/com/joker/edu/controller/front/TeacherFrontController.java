package com.joker.edu.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joker.commonutils.response.R;
import com.joker.edu.entity.EduCourse;
import com.joker.edu.entity.EduTeacher;
import com.joker.edu.service.EduCourseService;
import com.joker.edu.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {

    @Autowired private EduTeacherService teacherService;
    @Autowired private EduCourseService courseService;

    @GetMapping("/teacherPage/{page}/{limit}")
    public R getTeacherPageList(@PathVariable("page")long page,@PathVariable("limit")long limit){
        Page teacherPage=new Page(page,limit);
        Map<String,Object> map=teacherService.getTecherPageList(teacherPage);
        return R.ok().data(map);
    }

    @GetMapping("/getTeacherFrontInfo/{teacherId}")
    public R getTeacherInfo(@PathVariable("teacherId")String teacherId){
        EduTeacher byId = teacherService.getById(teacherId);
        QueryWrapper<EduCourse> queryWrapper=new QueryWrapper();
        queryWrapper.eq("teacher_id",teacherId);
        List<EduCourse> list = courseService.list(queryWrapper);
        return R.ok().data("teacher",byId).data("list",list);
    }
}
