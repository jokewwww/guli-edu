package com.joker.edu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.joker.commonutils.response.R;
import com.joker.edu.entity.EduTeacher;
import com.joker.edu.entity.vo.TeacherQuery;
import com.joker.edu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 讲师 前端控制器
 *
 * @author joker
 * @since 2020-08-11
 */
@Api(tags = "讲师管理接口")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

  @Autowired private EduTeacherService eduTeacherService;

  @ApiOperation("所有讲师列表")
  @GetMapping("findAll")
  public R findAll() {
    List<EduTeacher> list = eduTeacherService.list(null);
    return R.ok().data("items", list);
  }

  @ApiOperation("根据id逻辑删除讲师")
  @DeleteMapping("{id}")
  public R removeTeache(
      @ApiParam(name = "id", required = true, value = "讲师id") @PathVariable("id") String id) {
    return eduTeacherService.removeById(id) ? R.ok() : R.error();
  }

  @ApiOperation("添加讲师")
  @PostMapping("add")
  public R addTeache(@RequestBody EduTeacher eduTeacher) {

    return eduTeacher.insert() ? R.ok() : R.error();
  }

  @ApiOperation("根据id查询讲师")
  @PostMapping("getTeacher/{id}")
  public R queryTeache(@PathVariable String id) {
    EduTeacher eduTeacher = eduTeacherService.getById(id);
    return R.ok().data("teacher", eduTeacher);
  }

  @ApiOperation("根据条件分页查询讲师")
  @PostMapping("pageTeacher/{current}/{limit}")
  public R queryTeachers(
      @PathVariable("current") Long current,
      @PathVariable("limit") Long limit,
      @RequestBody TeacherQuery teacherQuery) {
    IPage<EduTeacher> pageTeacher = new Page(current, limit);
    QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
    String name = teacherQuery.getName();
    Integer level = teacherQuery.getLevel();
    String end = teacherQuery.getEnd();
    String begin = teacherQuery.getBegin();
    if (!StringUtils.isEmpty(name)) {
      queryWrapper.like("name", name);
    }
    if (!StringUtils.isEmpty(level)) {
      queryWrapper.eq("level", level);
    }
    if (!StringUtils.isEmpty(begin)) {
      queryWrapper.ge("gmt_create", begin); // >=
    }
    if (!StringUtils.isEmpty(begin)) {
      queryWrapper.lt("gmt_create", end); // <=
    }
    queryWrapper.orderByDesc("gmt_create");

    eduTeacherService.page(pageTeacher, queryWrapper);
    long total = pageTeacher.getTotal();
    List<EduTeacher> records = pageTeacher.getRecords();
    return R.ok().data("total", total).data("rows", records);
  }

  @ApiOperation("修改讲师信息")
  @PutMapping("modifyTeacher")
  public R modifyTeacheer(@RequestBody EduTeacher eduTeacher) {
    return eduTeacherService.updateById(eduTeacher) ? R.ok() : R.error();
  }

  @GetMapping("genQRCode")
  @ResponseBody
  @ApiOperation("根据用户信息生成二维码")
  public R genQRCode(HttpServletRequest request) {

    String content = "";

    int width = 300;
    int height = 300;
    String format = "png";

    // 定义二维码的参数
    Map hints = new HashMap();
    hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
    hints.put(EncodeHintType.MARGIN, 2);
    BitMatrix bitMatrix = null;
    // 生成二维码
    try {
      bitMatrix =
          new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
      Path file =
          new File("").toPath();
      MatrixToImageWriter.writeToPath(bitMatrix, format, file);
    } catch (Exception e) {

    }
    return R.ok();
  }

  @GetMapping("AnalyQRCode")
  @ResponseBody
  @ApiOperation("解析二维码")
  public Map test04(HttpServletRequest req) throws Exception {
    MultiFormatReader formatReader = new MultiFormatReader();
    File file = new File("");

    BufferedImage image = ImageIO.read(file);
    BinaryBitmap binaryBitmap =
        new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));

    // 定义二维码的参数
    HashMap hints = new HashMap();
    hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

    Result result = formatReader.decode(binaryBitmap, hints);

    System.out.println("二维码解析结果：" + result.toString());
    System.out.println("二维码的格式：" + result.getBarcodeFormat());
    System.out.println("二维码的文本内容：" + result.getText());

    Map map = new HashMap();
    map.put("text", result.getText());
    return map;
  }
}
