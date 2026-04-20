package com.joker.edu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joker.edu.entity.EduCourseDescription;
import com.joker.edu.mapper.EduCourseDescriptionMapper;
import com.joker.edu.service.EduCourseDescriptionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author joker
 * @since 2020-08-23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EduCourseDescriptionServiceImpl extends ServiceImpl<EduCourseDescriptionMapper, EduCourseDescription> implements EduCourseDescriptionService {

    @Override
    public void removeBycourseId(String courseId) {

    }
}
