package com.joker.edu.mapper;

import com.joker.edu.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joker.edu.entity.frontvo.CourseWebVo;
import com.joker.edu.entity.vo.CoursePublishVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author joker
 * @since 2020-08-23
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getCoursePublishInfo(String id);

    CourseWebVo getCourseFrontInfo(@Param("id") String id);
}
