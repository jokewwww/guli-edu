package com.joker.edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joker.edu.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

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
