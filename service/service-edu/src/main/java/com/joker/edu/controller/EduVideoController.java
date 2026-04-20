package com.joker.edu.controller;


import com.joker.commonutils.response.R;
import com.joker.edu.client.VodClient;
import com.joker.edu.entity.EduVideo;
import com.joker.edu.service.EduVideoService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author joker
 * @since 2020-08-23
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

    @ApiOperation("添加小节")
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }

    @ApiOperation("根据id删除小节")
    @DeleteMapping("delete/{id}")
    public R deleteById(@PathVariable String id){

        EduVideo video = videoService.getById(id);
        String videoSourceId = video.getVideoSourceId();
        if(StringUtils.isNotBlank(videoSourceId)){
            vodClient.removeVideo(videoSourceId);
        }

        videoService.removeById(id);
        return R.ok();
    }

    @GetMapping("getVideo/{videoId}")
    @ApiOperation("根据id查询小节信息")
    public R getVideoById(@PathVariable("videoId") String id){
        EduVideo video = videoService.getById(id);
        return R.ok().data("item",video);
    }

    @PutMapping("editVideo")
    @ApiOperation("根据id更新小节信息")
    public R updateVideo(@RequestBody EduVideo video){
         videoService.updateById(video);
        return R.ok();
    }

}

