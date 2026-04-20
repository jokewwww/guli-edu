package com.joker.edu.controller;


import com.joker.commonutils.response.R;
import com.joker.edu.entity.EduChapter;
import com.joker.edu.entity.vo.ChapterVO;
import com.joker.edu.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author joker
 * @since 2020-08-23
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
@Api(tags = "课程章节接口")
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    @PostMapping("/getChapterList/{courseId}")
    @ApiOperation("章节列表")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVO> list=chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",list);
    }

    @ApiOperation(value = "新增章节")
    @PostMapping("addChapter")
    public R save(
            @ApiParam(name = "chapter",value = "章节对象",required = true)
            @RequestBody EduChapter chapter){
        chapterService.save(chapter);
        return R.ok();
    }

    @ApiOperation("根据章节id查找章节信息")
    @GetMapping("getChapter/{id}")
    public R getById(
            @ApiParam(name = "id",value = "章节Id",required = true)
            @PathVariable String id){

        EduChapter eduChapter = chapterService.getById(id);
        return R.ok().data("item",eduChapter);
    }

    @ApiOperation("根据id修改章节")
    @PutMapping("updateChapter")
    public R update(
            @ApiParam(name = "eduChapter",value = "章节对象",required = true)
            @RequestBody EduChapter eduChapter){
        chapterService.updateById(eduChapter);
        return R.ok();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("根据id删除章节")
    public R removeById(
            @ApiParam(name = "id",value = "章节id",required = true)
            @PathVariable String id){
        boolean remove = chapterService.removeChapterById(id);
        return remove?R.ok():R.error().message("该章节下存在视频课程，请先删除视频课程");
    }

}

