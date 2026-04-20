package com.joker.edu.service;

import com.joker.edu.entity.EduCourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

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
