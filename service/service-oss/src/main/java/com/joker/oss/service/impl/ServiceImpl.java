package com.joker.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.joker.oss.service.OssService;
import com.joker.oss.utils.OssPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;


@Service
public class ServiceImpl implements OssService {
    @Override
    public String uploadAvatar(MultipartFile file) {
        String endpoint= OssPropertiesUtil.END_POINT;
        String accessKeyId= OssPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret= OssPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName= OssPropertiesUtil.BUCKET_NAME;

        if(true){ //oss不可用
            return "https://www.pianshen.com/thumbs/330/e40a9d7bb47c97746c2a3d346fe5aa8a.JPEG";
        }

        try {
            OSS oss = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            InputStream inputStream =file.getInputStream();
            //上传文件
            /**
             * bucketName，文件名称或者文件路径 输入流
             */
            String fileName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName=uuid+fileName;
            //根据日期分包（文件夹）
            String datePath = new DateTime().toString("yyyy/MM/dd");
            fileName=datePath+"/"+fileName;

            oss.putObject(bucketName, fileName, inputStream);

            oss.shutdown();
            String url="https://"+bucketName+"."+endpoint+"/"+fileName;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
