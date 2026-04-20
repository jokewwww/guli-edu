package com.joker.edu.controller;


import com.joker.commonutils.response.R;
import com.joker.edu.entity.CrmBanner;
import com.joker.edu.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cmsservice/bannerfront")
@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("bannerList")
    public R getAllBanner(){
        List<CrmBanner> list=bannerService.selectAllBanner();
        return R.ok().data("list",list);
    }
}
