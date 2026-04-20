package com.joker.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joker.edu.entity.EduSubject;
import com.joker.edu.entity.tree.OneSubject;
import com.joker.edu.entity.tree.TwoSubject;
import com.joker.edu.excel.SubjectExcel;
import com.joker.edu.excel.SubjectExcelListener;
import com.joker.edu.mapper.EduSubjectMapper;
import com.joker.edu.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author joker
 * @since 2020-08-22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void addExcelSubject(MultipartFile file,EduSubjectService subjectService) {
       try{
           InputStream inputStream = file.getInputStream();

           EasyExcel.read(inputStream, SubjectExcel.class,new SubjectExcelListener(subjectService)).sheet().doRead();
       }catch (Exception e){

       }
    }

    @Override
    public List<OneSubject> getAllSubject() {
        QueryWrapper<EduSubject> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("parent_id","0");
        List<EduSubject> oneSubjects = baseMapper.selectList(queryWrapper);

        queryWrapper=new QueryWrapper<>();
        queryWrapper.ne("parent_id","0");
        List<EduSubject> twoEduSubjects = baseMapper.selectList(queryWrapper);

        List<OneSubject> finalSubjectList=new ArrayList<>();

        oneSubjects.stream().forEach(eduSubject -> {
            finalSubjectList.add(new OneSubject() {{
                setId(eduSubject.getId());
                setTitle(eduSubject.getTitle());
            }});
        });


    finalSubjectList.stream().forEach(
            oneSubject -> {
                List<TwoSubject> twoSubjects=new ArrayList<>();
                List<EduSubject> s = twoEduSubjects.stream().filter(twoSubject -> twoSubject.getParentId().equals(oneSubject.getId()))
                        .collect(Collectors.toList());
                s.stream().forEach(e -> {
                    twoSubjects.add(new TwoSubject(){{
                        setId(e.getId());
                        setTitle(e.getTitle());
                    }});

                });
              oneSubject.setChildren(twoSubjects);
            });

        return finalSubjectList;
    }


}
