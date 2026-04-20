package com.joker.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joker.edu.entity.EduSubject;
import com.joker.edu.entity.tree.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author joker
 * @since 2020-08-22
 */
public interface EduSubjectService extends IService<EduSubject> {

    void addExcelSubject(MultipartFile file,EduSubjectService subjectService);

    List<OneSubject> getAllSubject();
}
