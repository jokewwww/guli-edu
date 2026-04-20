package com.joker.edu.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joker.commonutils.response.R;
import com.joker.commonutils.response.orderVo.CourseWebVoOrder;
import com.joker.commonutils.util.JwtUtils;
import com.joker.edu.client.OrderClient;
import com.joker.edu.entity.EduCourse;
import com.joker.edu.entity.frontvo.CourseQueryVo;
import com.joker.edu.entity.frontvo.CourseWebVo;
import com.joker.edu.entity.vo.ChapterVO;
import com.joker.edu.service.EduChapterService;
import com.joker.edu.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@Api(tags = "前台课程接口")
@RequestMapping("/eduservice/coursefront")
public class CourseFrontController {


    @Autowired private EduCourseService courseService;
    @Autowired private EduChapterService chapterService;
    @Autowired private OrderClient orderClient;

    @ApiOperation("课程首页数据")
    @PostMapping("/coursePage/{page}/{limit}")
    public R getTeacherPageList(@PathVariable("page")long page, @PathVariable("limit")long limit,
                                @RequestBody(required = false)CourseQueryVo courseQueryVo){
        Page<EduCourse> coursePage = new Page<>(page, limit);

        Map<String, Object> map=courseService.getCoursePageList(coursePage,courseQueryVo);
        return R.ok().data(map);
    }

    @GetMapping("getCourseFrontInfo/{id}")
    public R getCourseFrontInfo(@PathVariable String id, HttpServletRequest request){
        //查询课程信息和讲师信息
        CourseWebVo courseWebVo = courseService.selectInfoWebById(id);

        //查询当前课程的章节信息
        List<ChapterVO> chapterVoList = chapterService.getChapterVideoByCourseId(id);
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        boolean buyCourse = orderClient.isBuyCourse(id, memberId);

        return R.ok().data("course",courseWebVo).data("chapterList",chapterVoList).data("isBuy",buyCourse);
    }

    @ApiOperation("根据Id获取课程信息")
    @PostMapping("getCourseOrder/{id}")
    public CourseWebVoOrder getCourse(@PathVariable String id){
        CourseWebVo courseWebVo = courseService.selectInfoWebById(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseWebVo,courseWebVoOrder);
        return courseWebVoOrder;
    }
}
