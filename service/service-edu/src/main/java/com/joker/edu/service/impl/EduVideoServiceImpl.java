package com.joker.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joker.edu.client.VodClient;
import com.joker.edu.entity.EduVideo;
import com.joker.edu.mapper.EduVideoMapper;
import com.joker.edu.service.EduVideoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author joker
 * @since 2020-08-23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired private VodClient vodClient;

    @Override
    public boolean getCountByChapterId(String chapterId) {
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper();
        queryWrapper.eq("chapter_id",chapterId);
        Integer count = baseMapper.selectCount(queryWrapper);
        return null!=count&&count>0;
    }

    @Override
    public void removeBycourseId(String courseId) {
        QueryWrapper<EduVideo> queryWrapper=new QueryWrapper(){{
            eq("course_id",courseId);
            select("video_sourse_id");
        }};
        List<EduVideo> eduVideos = baseMapper.selectList(queryWrapper);
    List<String> collect =
        eduVideos.stream()
            .filter(eduVideo -> StringUtils.isNotEmpty(eduVideo.getVideoSourceId()))
            .map(EduVideo::getVideoSourceId)
            .collect(Collectors.toList());

    if(collect.size()>0){
        vodClient.deleteBatch(collect);}
    }
}
