package com.joker.edu.service;

import com.joker.edu.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author joker
 * @since 2020-08-23
 */
public interface EduVideoService extends IService<EduVideo> {
    boolean getCountByChapterId(String chapterId);

    void removeBycourseId(String courseId);
}
