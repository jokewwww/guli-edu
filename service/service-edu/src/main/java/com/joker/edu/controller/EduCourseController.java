package com.joker.edu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joker.commonutils.response.R;
import com.joker.edu.entity.EduCourse;
import com.joker.edu.entity.vo.CourseInfoForm;
import com.joker.edu.entity.vo.CoursePublishVo;
import com.joker.edu.entity.vo.CourseQuery;
import com.joker.edu.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程 前端控制器
 *
 * @author joker
 * @since 2020-08-23
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

  @Autowired private EduCourseService eduCourseService;

  @PostMapping("addCourseInfo")
  @ApiOperation("添加课程")
  public R addCourse(@RequestBody CourseInfoForm courseInfoForm) {
    String courseId = eduCourseService.addCource(courseInfoForm);
    return R.ok().data("id", courseId);
  }

  @GetMapping("getCourse/{id}")
  @ApiOperation("根据课程id查询课程信息")
  public R getCourseInfo(@PathVariable String id) {
    CourseInfoForm courseInfoForm = eduCourseService.getCourseInfo(id);
    return R.ok().data("courseInfoForm", courseInfoForm);
  }

  @ApiOperation("修改课程信息")
  @PutMapping("updateCourse")
  public R updateCourse(@RequestBody CourseInfoForm courseInfoForm) {
    eduCourseService.updateCourse(courseInfoForm);
    return R.ok();
  }

  @ApiOperation("课程发布信息")
  @GetMapping("coursePublish/{id}")
  public R getCoursePublishInfo(@PathVariable String id) {
    CoursePublishVo coursePublishVo = eduCourseService.getCoursePublishInfo(id);
    return R.ok().data("item", coursePublishVo);
  }

  @ApiOperation("发布课程")
  @GetMapping("publishCourse/{id}")
  public R publishCourse(@PathVariable String id) {
    EduCourse eduCourse =
        new EduCourse() {
          {
            setId(id);
            setStatus("Normal");
          }
        };
    eduCourseService.updateById(eduCourse);
    return R.ok();
  }

  @ApiOperation("课程列表")
  @PostMapping("courseList/{current}/{limit}")
  public R courseList(@PathVariable("current") Long current,
                      @PathVariable("limit")Long limit,
                      @RequestBody CourseQuery courseQuery) {

      String name = courseQuery.getName();
      String status = courseQuery.getStatus();
      String start = courseQuery.getStart();
      String end = courseQuery.getEnd();

      QueryWrapper<EduCourse> queryWrapper = new QueryWrapper();
      if(!StringUtils.isEmpty(name)){
          queryWrapper.like("title",name);
      }
      if(!StringUtils.isEmpty(status)){
          queryWrapper.eq("status",status);
      }
      if(!StringUtils.isEmpty(start)){
          queryWrapper.ge("gmt_create",start);//  >=
      }
      if(!StringUtils.isEmpty(end)){
          queryWrapper.lt("gmt_create",end);// <=
      }
      queryWrapper.orderByDesc("gmt_create");

      Page<EduCourse> coursePage = new Page<>(current, limit);
      eduCourseService.page(coursePage,queryWrapper);
      List<EduCourse> records = coursePage.getRecords();
      long total = coursePage.getTotal();
      return R.ok().data("list",records).data("total",total);
  }


    @ApiOperation("删除课程")
    @DeleteMapping("delete/{id}")
    public R deleteCourseById(@PathVariable String id) {
         eduCourseService.deleteCourse(id);
        return R.ok();
    }
}
