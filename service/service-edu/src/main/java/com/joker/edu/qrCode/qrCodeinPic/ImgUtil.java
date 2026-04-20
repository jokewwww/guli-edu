package com.joker.edu.qrCode.qrCodeinPic;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

/**
 * 图片中嵌入二维码
 */
public class ImgUtil {
    /**
     * 定义二维码图片的宽度
     */
    private static final int WIDTH = 125;
    /**
     * 定义二维码图片的高度
     */
    private static final int HEIGHT = 125;

    /**
     * 定义LOGO图片的宽度
     */
    private static final int LOGO_WIDTH = 40;
    /**
     * 定义LOGO图片的高度
     */
    private static final int LOGO_HEIGHT = 40;


    /**
     * 生成二维码的方法
     */
    public static BufferedImage execute() throws Exception {
        /** 判断二维码中URL */
        String url =null;
        if (url == null || "".equals(url)) {
            url = "你好啊骚猪！";
        }

        /** 定义Map集合封装二维码配置信息 */
        Map<EncodeHintType, Object> hints = new HashMap<>();
        /** 设置二维码图片的内容编码 */
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        /** 设置二维码图片的上、下、左、右间隙 */
        hints.put(EncodeHintType.MARGIN, 1);
        /** 设置二维码的纠错级别 */
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        /**
         * 创建二维码字节转换对象
         * 第一个参数：二维码图片中的内容
         * 第二个参数：二维码格式器
         * 第三个参数：生成二维码图片的宽度
         * 第四个参数：生成二维码图片的高度
         * 第五个参数：生成二维码需要配置信息
         *  */
        BitMatrix matrix = new MultiFormatWriter().encode(url,
                BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);

        /** 获取二维码图片真正的宽度  */
        int matrix_width = matrix.getWidth();
        /** 获取二维码图片真正的高度  */
        int matrix_height = matrix.getHeight();
        /** 定义一张空白的缓冲流图片 */
        BufferedImage image = new BufferedImage(matrix_width, matrix_height,
                BufferedImage.TYPE_INT_RGB);
        /** 把二维码字节转换对象 转化 到缓冲流图片上 */
        for (int x = 0; x < matrix_width; x++) {
            for (int y = 0; y < matrix_height; y++) {
                /** 通过x、y坐标获取一点的颜色 true: 黑色  false: 白色 */
                int rgb = matrix.get(x, y) ? 0x000000 : 0xFFFFFF;
                image.setRGB(x, y, rgb);
            }
        }



        /** 获取公司logo图片 */
//        BufferedImage logo = ImageIO.read(new ClassPathResource("貂蝉.jpg").getInputStream());
        BufferedImage logo = ImageIO.read(new FileInputStream(new File("D:\\图片\\貂蝉.jpg")));
        /** 获取缓冲流图片的画笔 */
        Graphics2D g = (Graphics2D) image.getGraphics();
        /** 在二维码图片中间绘制公司logo */
        g.drawImage(logo, (matrix_width - LOGO_WIDTH) / 2,
                (matrix_height - LOGO_HEIGHT) / 2, LOGO_WIDTH, LOGO_HEIGHT, null);

        /** 设置画笔的颜色 */
        g.setColor(Color.WHITE);
        /** 设置画笔的粗细 */
        g.setStroke(new BasicStroke(5.0f));
        /** 设置消除锯齿 */
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        /** 绘制圆角矩形 */
        g.drawRoundRect((matrix_width - LOGO_WIDTH) / 2, (matrix_height - LOGO_HEIGHT) / 2,
                LOGO_WIDTH, LOGO_HEIGHT, 10, 10);
        return image;
    }

    // 二维码和图片的合成
    public static final void combineCodeAndPicToFile() {
        long starttime = System.currentTimeMillis();
        System.out.println("开始合成：" + starttime);
        try {
      // 背景图
      // BufferedImage big = ImageIO.read(new File(backPicPath));
      // BufferedImage big = ImageIO.read(new
      // ClassPathResource("static/img/456.jpg").getInputStream());
      BufferedImage big = ImageIO.read(new FileInputStream(new File("D:\\图片\\16.jpg")));
            //二维码的图片
            //url扫二维码显示的内容
            BufferedImage small = execute();
            Graphics2D g = big.createGraphics();  //合成的图片

            //二维码或小图在大图的左上角坐标
            int x = big.getWidth() - small.getWidth()-45; //加是向右，减是向左
            int y = (big.getHeight() - small.getHeight()-50); //加是向下，减是向上
            //将二维码画在背景图上
            g.drawImage(small, x, y, small.getWidth(), small.getHeight(), null);
            //结束绘画
            g.dispose();
            //为了保证大图背景不变色，formatName必须为"png"
            ImageIO.write(big, "png", new FileOutputStream("111.jpg"));

            System.out.println("结束合成：" + (System.currentTimeMillis() - starttime) / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //合成base64响应前台图片数据展示
    public static final String combineCodeAndPicToBase64(MultipartFile file, HttpServletResponse response) {
        ImageOutputStream imOut = null;
        try {
            BufferedImage big = ImageIO.read(file.getInputStream());
            // BufferedImage small = ImageIO.read(new File(fillPicPath));
            BufferedImage small = ImgUtil.execute();
            Graphics2D g = big.createGraphics();

            //左下角位置
           /* int x = big.getWidth() - small.getWidth()-45; //加是向右，减是向左
            int y = (big.getHeight() - small.getHeight()-50); //加是向下，减是向上*/

            //右下角位置
           /* int x = 45; //加是向右，减是向左
            int y = (big.getHeight() - small.getHeight()-50); //加是向下，减是向上*/
            //居中位置
            int x = (big.getWidth() - small.getWidth())/2; //加是向右，减是向左
            int y = (big.getHeight() - small.getHeight()-50); //加是向下，减是向上

            g.drawImage(small, x, y, small.getWidth(), small.getHeight(), null);
            g.dispose();
            //为了保证大图背景不变色，formatName必须为"png"
            //字节数组流
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            //将图片流转换为字节数组流
            imOut = ImageIO.createImageOutputStream(bs);
            //将合成好的背景图输入到字节数组流中
            ImageIO.write(big, "png", imOut);
            //将字节数组流转换为二进制流
            InputStream in = new ByteArrayInputStream(bs.toByteArray());

            byte[] bytes = new byte[in.available()];
            //读取数组流中的数据
            in.read(bytes);
            //转换为base64数据类型
            String base64 = Base64.getEncoder().encodeToString(bytes);
            System.out.println(base64);

            return "data:image/jpeg;base64," + base64;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

        combineCodeAndPicToFile();
    }
}