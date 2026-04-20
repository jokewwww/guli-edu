package com.joker.edu.qrCode;



import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** 二维码工具类 */
public class ZxingUtils {

  public static BufferedImage enQRCode(String contents, int width, int height)
      throws WriterException {
    // 定义二维码参数
    final Map<EncodeHintType, Object> hints =
        new HashMap(8) {
          {
            // 编码
            put(EncodeHintType.CHARACTER_SET, "UTF-8");
            // 容错级别
            put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            // 边距
            put(EncodeHintType.MARGIN, 0);
          }
        };
    return enQRCode(contents, width, height, hints);
  }

  /**
   * 生成二维码
   *
   * @param contents 二维码内容
   * @param width 图片宽度
   * @param height 图片高度
   * @param hints 二维码相关参数
   * @return BufferedImage对象
   * @throws WriterException 编码时出错
   * @throws IOException 写入文件出错
   */
  public static BufferedImage enQRCode(String contents, int width, int height, Map hints)
      throws WriterException {
    //        String uuid = UUID.randomUUID().toString().replace("-", "");
    // 本地完整路径
    //        String pathname = path + "/" + uuid + "." + format;
    // 生成二维码
    BitMatrix bitMatrix =
        new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);
    //        Path file = new File(pathname).toPath();
    // 将二维码保存到路径下
    //        MatrixToImageWriter.writeToPa(bitMatrix, format, file);
    //        return pathname;
    return MatrixToImageWriter.toBufferedImage(bitMatrix);
  }

  /**
   * 将图片绘制在背景图上
   *
   * @param backgroundPath 背景图路径
   * @param zxingImage 图片
   * @param x 图片在背景图上绘制的x轴起点
   * @param y 图片在背景图上绘制的y轴起点
   * @return
   */
  public static BufferedImage drawImage(
      String backgroundPath, BufferedImage zxingImage, int x, int y) throws IOException {
    // 读取背景图的图片流
    BufferedImage backgroundImage;
    // Try-with-resources 资源自动关闭,会自动调用close()方法关闭资源,只限于实现Closeable或AutoCloseable接口的类
    try (InputStream imagein = new FileInputStream(new File(backgroundPath))) {
      backgroundImage = ImageIO.read(imagein);
    }
    return drawImage(backgroundImage, zxingImage, x, y);
  }

  /**
   * 将图片绘制在背景图上
   *
   * @param backgroundImage 背景图
   * @param zxingImage 图片
   * @param x 图片在背景图上绘制的x轴起点
   * @param y 图片在背景图上绘制的y轴起点
   * @return
   * @throws IOException
   */
  public static BufferedImage drawImage(
      BufferedImage backgroundImage, BufferedImage zxingImage, int x, int y) throws IOException {
    Objects.requireNonNull(backgroundImage, ">>>>>背景图不可为空");
    Objects.requireNonNull(zxingImage, ">>>>>二维码不可为空");
    // 二维码宽度+x不可以超过背景图的宽度,长度同理

    int totalHeight = zxingImage.getHeight() + y;
    int totalWidth = zxingImage.getWidth() + x;

    if ((zxingImage.getWidth()) > backgroundImage.getWidth()
        || (zxingImage.getHeight()) > backgroundImage.getHeight()) {
      throw new IOException(">>>>>二维码宽度+x不可以超过背景图的宽度,长度同理");
    }

    // 合并图片
    Graphics2D g = backgroundImage.createGraphics();
    g.drawImage(zxingImage, x, totalHeight, zxingImage.getWidth(), zxingImage.getHeight(), null);
    return backgroundImage;
  }

  /**
   * 将文字绘制在背景图上
   *
   * @param backgroundImage 背景图
   * @param x 文字在背景图上绘制的x轴起点
   * @param y 文字在背景图上绘制的y轴起点
   * @return
   * @throws IOException
   */
  public static BufferedImage drawString(
      BufferedImage backgroundImage, String text, int x, int y, Font font, Color color) {
    // 绘制文字
    Graphics2D g = backgroundImage.createGraphics();
    // 设置颜色
    g.setColor(color);
    // 消除锯齿状
    g.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
    // 设置字体
    g.setFont(font);
    // 绘制文字
    g.drawString(text, x, y);
    g.drawString("o", x, y);
    return backgroundImage;
  }

  public static InputStream bufferedImageToInputStream(BufferedImage backgroundImage)
      throws IOException {
    return bufferedImageToInputStream(backgroundImage, "png");
  }

  /**
   * backgroundImage 转换为输出流
   *
   * @param backgroundImage
   * @param format
   * @return
   * @throws IOException
   */
  public static InputStream bufferedImageToInputStream(BufferedImage backgroundImage, String format)
      throws IOException {
    ByteArrayOutputStream bs = new ByteArrayOutputStream();
    try (ImageOutputStream imOut = ImageIO.createImageOutputStream(bs)) {
      ImageIO.write(backgroundImage, format, imOut);
      InputStream is = new ByteArrayInputStream(bs.toByteArray());
      return is;
    }
  }

  /**
   * 保存为文件
   *
   * @param is
   * @param fileName
   * @throws IOException
   */
  public static void saveFile(InputStream is, String fileName) throws IOException {
    try (BufferedInputStream in = new BufferedInputStream(is);
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fileName))) {
      int len;
      byte[] b = new byte[1024];
      while ((len = in.read(b)) != -1) {
        out.write(b, 0, len);
      }
    }
  }

  public static Map saveToFile(String destUrl) {
    FileOutputStream fos = null;
    BufferedInputStream bis = null;
    HttpURLConnection httpUrl = null;
    URL url = null;
    int BUFFER_SIZE = 1024;
    byte[] buf = new byte[BUFFER_SIZE];
    int size = 0;
    String filePath =
        "D:\\qycache\\WeChat Files\\wxid_ooc5fm0cdu8122\\FileStorage\\File\\2020-08\\二维码\\abc.jpg";
    try {
      url = new URL(destUrl.replace(";", ""));
      httpUrl = (HttpURLConnection) url.openConnection();
      httpUrl.connect();
      bis = new BufferedInputStream(httpUrl.getInputStream());
      fos = new FileOutputStream(new File(filePath));
      while ((size = bis.read(buf)) != -1) {
        fos.write(buf, 0, size);
      }
      fos.flush();

      BufferedImage read = ImageIO.read(new File(filePath));
      int width = read.getWidth(); //照片的宽度
      int height = read.getHeight();//照片的长度

      fos.close();
        bis.close();
        httpUrl.disconnect();

      Map<String,String> map = new HashMap() {{
        put("filePath", filePath+"");
        put("width", width+"");
        put("height", height+"");
      }};

      return map;

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

  }
}
