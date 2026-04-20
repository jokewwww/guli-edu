package com.joker.edu.service;

import com.joker.edu.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joker.edu.entity.vo.ChapterVO;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author joker
 * @since 2020-08-23
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVO> getChapterVideoByCourseId(String courseId);

    boolean removeChapterById(String id);

    void removeBycourseId(String courseId);
}
