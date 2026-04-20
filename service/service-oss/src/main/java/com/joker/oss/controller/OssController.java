package com.joker.oss.controller;


import com.joker.commonutils.response.R;
import com.joker.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/eduoss/fileoss")
@Api(tags = "oss文件上传")
public class OssController {

    @Autowired
    private OssService ossService;


    @PostMapping
    @ApiOperation("上传头像")
    public R uploadAvatar(MultipartFile file){
        String url=ossService.uploadAvatar(file);
        return R.ok().data("url",url);
    }
}
