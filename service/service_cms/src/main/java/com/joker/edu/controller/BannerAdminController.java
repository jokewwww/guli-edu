package com.joker.edu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joker.commonutils.response.R;
import com.joker.edu.entity.CrmBanner;
import com.joker.edu.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cmsservice/banneradmin")
@CrossOrigin
public class BannerAdminController {
  @Autowired
  private CrmBannerService bannerService;

  @ApiOperation(value = "获取Banner分页列表")
  @GetMapping("{page}/{limit}")
  public R index(
      @ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
      @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit) {
    Page<CrmBanner> pageParam = new Page<>(page, limit);
    bannerService.page(pageParam, null);
    return R.ok().data("items", pageParam.getRecords()).data("total", pageParam.getTotal());
  }

  @ApiOperation(value = "获取Banner")
  @GetMapping("get/{id}")
  public R get(@PathVariable String id) {
    CrmBanner banner = bannerService.getById(id);
    return R.ok().data("item", banner);
  }

  @ApiOperation(value = "新增Banner")
  @PostMapping("save")
  public R save(@RequestBody CrmBanner banner) {
    bannerService.save(banner);
    return R.ok();
  }

  @ApiOperation(value = "修改Banner")
  @PutMapping("update")
  public R updateById(@RequestBody CrmBanner banner) {
    bannerService.updateById(banner);
    return R.ok();
  }

  @ApiOperation(value = "删除Banner")
  @DeleteMapping("remove/{id}")
  public R remove(@PathVariable String id) {
    bannerService.removeById(id);
    return R.ok();
  }
}
