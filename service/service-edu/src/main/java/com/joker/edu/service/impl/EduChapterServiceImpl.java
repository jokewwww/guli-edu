package com.joker.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joker.edu.entity.EduChapter;
import com.joker.edu.entity.EduVideo;
import com.joker.edu.entity.vo.ChapterVO;
import com.joker.edu.entity.vo.VideoVO;
import com.joker.edu.mapper.EduChapterMapper;
import com.joker.edu.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joker.edu.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;


    @Override
    public List<ChapterVO> getChapterVideoByCourseId(String courseId) {
        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper();
        queryWrapper.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(queryWrapper);

        QueryWrapper<EduVideo> videoQueryWrapper=new QueryWrapper();
        videoQueryWrapper.eq("course_id",courseId);
        List<EduVideo> eduVideoList = videoService.list(videoQueryWrapper);

        List<ChapterVO> finalChapterList=new ArrayList();
        eduChapterList.stream().forEach(eduChapter -> {
            finalChapterList.add(new ChapterVO(){{
                setId(eduChapter.getId());
                setTitle(eduChapter.getTitle());
            }});
        });



            finalChapterList.stream().forEach(chapterVO -> {
                List<VideoVO> videoVOList =new ArrayList();
                List<EduVideo> eduVideos=eduVideoList.stream().filter(eduVideo -> eduVideo.getChapterId().equals(chapterVO.getId())).collect(Collectors.toList());
                eduVideos.stream().forEach(eduVideo->videoVOList.add(new VideoVO(){{
                    setId(eduVideo.getId());
                    setTitle(eduVideo.getTitle());
                    setVideoSourceId(eduVideo.getVideoSourceId());
                }}));
                chapterVO.setChildren(videoVOList);
            });

        return finalChapterList;
    }

    @Override
    public boolean removeChapterById(String id) {
        boolean countByChapterId = videoService.getCountByChapterId(id);
        if(countByChapterId){
            return false;
        }
        int delete = baseMapper.deleteById(id);
        return delete==1;
    }

    @Override
    public void removeBycourseId(String courseId) {

    }
}
