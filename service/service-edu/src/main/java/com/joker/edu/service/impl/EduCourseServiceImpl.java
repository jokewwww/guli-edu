package com.joker.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joker.edu.entity.EduCourse;
import com.joker.edu.entity.EduCourseDescription;
import com.joker.edu.entity.frontvo.CourseQueryVo;
import com.joker.edu.entity.frontvo.CourseWebVo;
import com.joker.edu.entity.vo.CourseInfoForm;
import com.joker.edu.entity.vo.CoursePublishVo;
import com.joker.edu.mapper.EduCourseMapper;
import com.joker.edu.service.EduChapterService;
import com.joker.edu.service.EduCourseDescriptionService;
import com.joker.edu.service.EduCourseService;
import com.joker.edu.service.EduVideoService;
import com.joker.servicebase.exception.GuliException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author joker
 * @since 2020-08-23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired private EduCourseDescriptionService courseDescriptionService;
    @Autowired private EduVideoService eduVideoService;
    @Autowired private EduChapterService chapterService;
    @Autowired private EduCourseDescriptionService descriptionService;
    //添加课程信息
    @Override
    public String addCource(CourseInfoForm courseInfoForm) {
        EduCourse eduCourse=new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);

        if(baseMapper.insert(eduCourse)==0){
            throw new GuliException(20001,"添加课程信息失败");
        }
        EduCourseDescription courseDescription=new EduCourseDescription(){{
           setDescription(courseInfoForm.getDescription());
            setId(eduCourse.getId());
        }};
        courseDescriptionService.save(courseDescription);
        return eduCourse.getId();
    }

    @Override
    public CourseInfoForm getCourseInfo(String id) {
        EduCourse eduCourse = baseMapper.selectById(id);
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(eduCourse,courseInfoForm);

        EduCourseDescription courseDescription = courseDescriptionService.getById(id);
        courseInfoForm.setDescription(courseDescription.getDescription());
        return courseInfoForm;
    }

    @Override
    public void updateCourse(CourseInfoForm courseInfoForm) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if(1!=update){
            throw new GuliException(20002,"更新课程表信息失败");
        }
        EduCourseDescription courseDescription = new EduCourseDescription() {{
            setId(courseInfoForm.getId());
            setDescription(courseInfoForm.getDescription());
        }};
        courseDescriptionService.updateById(courseDescription);
    }

    @Override
    public CoursePublishVo getCoursePublishInfo(String id) {
        return baseMapper.getCoursePublishInfo(id);
    }

    @Override
    public void deleteCourse(String courseId) {
        //删除小节
        eduVideoService.removeBycourseId(courseId);
        //删除章节
        chapterService.removeBycourseId(courseId);
        //删除课程描述
        descriptionService.removeBycourseId(courseId);
        //删除课程
        int i = baseMapper.deleteById(courseId);
        if(i==0){
            throw new GuliException(20001,"删除课程失败");
        }
    }

    @Override
    public Map<String, Object> getCoursePageList(Page<EduCourse> coursePage, CourseQueryVo courseQueryVo) {
        QueryWrapper<EduCourse> queryWrapper=new QueryWrapper();

        if(null!=courseQueryVo){
            String subjectParentId = courseQueryVo.getSubjectParentId();
            String subjectId = courseQueryVo.getSubjectId();
            String priceSort = courseQueryVo.getPriceSort();
            String buyCountSort = courseQueryVo.getBuyCountSort();
            String gmtCreateSort = courseQueryVo.getGmtCreateSort();

            if(StringUtils.isNotBlank(subjectParentId)){
                queryWrapper.eq("subject_parent_id",subjectParentId);
            }
            if(StringUtils.isNotBlank(subjectId)){
                queryWrapper.eq("subject_id",subjectId);
            }
            if(StringUtils.isNotBlank(priceSort)){
                queryWrapper.orderByDesc("price");
            }
            if(StringUtils.isNotBlank(buyCountSort)){
                queryWrapper.orderByDesc("buy_count");
            }
            if(StringUtils.isNotBlank(gmtCreateSort)){
                queryWrapper.orderByDesc("gmt_create");
            }
        }

        baseMapper.selectPage(coursePage,queryWrapper);

        List<EduCourse> records = coursePage.getRecords();
        long current = coursePage.getCurrent();
        long pages = coursePage.getPages();
        long size = coursePage.getSize();
        long total = coursePage.getTotal();
        boolean hasNext = coursePage.hasNext();//下一页
        boolean hasPrevious = coursePage.hasPrevious();//上一页

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

    @Override
    public CourseWebVo selectInfoWebById(String id) {
        return baseMapper.getCourseFrontInfo(id);
    }
}
