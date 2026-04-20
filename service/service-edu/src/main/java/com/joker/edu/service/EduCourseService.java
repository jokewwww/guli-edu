package com.joker.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joker.edu.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joker.edu.entity.frontvo.CourseQueryVo;
import com.joker.edu.entity.frontvo.CourseWebVo;
import com.joker.edu.entity.vo.CourseInfoForm;
import com.joker.edu.entity.vo.CoursePublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author joker
 * @since 2020-08-23
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCource(CourseInfoForm courseInfoForm);

    /**
     * 根据课程id查询课程信息
     * @param id
     * @return
     */
    CourseInfoForm getCourseInfo(String id);

    /**
     * 修改课程信息
     * @param courseInfoForm
     */
    void updateCourse(CourseInfoForm courseInfoForm);

    CoursePublishVo getCoursePublishInfo(String id);

    /**
     * 根据id删除课程
     * @param id
     */
    void deleteCourse(String id);

    Map<String, Object> getCoursePageList(Page<EduCourse> coursePage, CourseQueryVo courseQueryVo);

    CourseWebVo selectInfoWebById(String id);
}
