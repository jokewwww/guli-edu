package com.joker.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joker.edu.entity.EduTeacher;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author joker
 * @since 2020-08-11
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String, Object> getTecherPageList(Page teacherPage);

}
