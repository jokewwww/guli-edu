package com.joker.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joker.edu.entity.EduCourseDescription;

/**
 * <p>
 * 课程简介 服务类
 * </p>
 *
 * @author joker
 * @since 2020-08-23
 */
public interface EduCourseDescriptionService extends IService<EduCourseDescription> {

    void removeBycourseId(String courseId);
}
