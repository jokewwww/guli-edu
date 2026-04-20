package com.joker.edu.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joker.edu.entity.EduSubject;
import com.joker.edu.service.EduSubjectService;
import com.joker.servicebase.exception.GuliException;


public class SubjectExcelListener extends AnalysisEventListener<SubjectExcel> {

    private EduSubjectService eduSubjectService;
    public SubjectExcelListener(){}
    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invoke(SubjectExcel subjectExcel, AnalysisContext analysisContext) {

        if(null==subjectExcel){
            throw new GuliException(20001,"excel数据为空");
        }
        //一行一行读取，每次读取一行数据，第一个值是一级分类，第二个值是二级分类
        EduSubject existOneSubject = existOneSubject(subjectExcel.getOneSubjectName(), eduSubjectService);
        if(null==existOneSubject){
            existOneSubject=new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectExcel.getOneSubjectName());
            eduSubjectService.save(existOneSubject);
        }

        String pid=existOneSubject.getId();
        EduSubject existTwoSubject = existTwoSubject(subjectExcel.getTwoSubjectName(), eduSubjectService, pid);
        if(null==existTwoSubject){
            existTwoSubject=new EduSubject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectExcel.getTwoSubjectName());
            eduSubjectService.save(existTwoSubject);
        }
    }

    //判断一级分类不能重复添加
    private EduSubject existOneSubject(String name,EduSubjectService subjectService){
        QueryWrapper<EduSubject> queryWrapper=new QueryWrapper();
        queryWrapper.eq("title",name);
        queryWrapper.eq("parent_id","0");
        EduSubject existOneSubject = subjectService.getOne(queryWrapper);
        return existOneSubject;
    }

    //判断二级分类不能重复添加
    private EduSubject existTwoSubject(String name,EduSubjectService subjectService,String pId){
        QueryWrapper<EduSubject> queryWrapper=new QueryWrapper();
        queryWrapper.eq("title",name);
        queryWrapper.eq("parent_id",pId);
        EduSubject existTwoSubject = subjectService.getOne(queryWrapper);
        return existTwoSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
