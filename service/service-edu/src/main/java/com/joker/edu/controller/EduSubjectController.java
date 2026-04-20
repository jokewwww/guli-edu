package com.joker.edu.controller;


import com.joker.commonutils.response.R;
import com.joker.edu.entity.tree.OneSubject;
import com.joker.edu.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author joker
 * @since 2020-08-22
 */
@RestController
@RequestMapping("/eduservice/subject")
@Api(tags = "课程分类接口")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    @PostMapping("/addSubject")
    @ApiOperation("添加课程分类")
    public R addSubject(MultipartFile file){
        subjectService.addExcelSubject(file,subjectService);
        return R.ok();
    }

    @GetMapping("getAllSubject")
    @ApiOperation("树形课程分类列表")
    public R subjectList(){
        List<OneSubject> oneSubjectList=subjectService.getAllSubject();
        return R.ok().data("list",oneSubjectList);
    }

    @ApiOperation("非json数据")
    @PostMapping("index")
    @ApiImplicitParam("ssssd")
    public String index(HttpServletRequest request){
        String header = request.getHeader("content-type");

        System.out.println(header);
        return "success";
    }

    private void print(Temp fun){
        fun.print();
    }

    @FunctionalInterface
    interface Temp{
        void print();
    }
}

