package com.joker.edu.qrCode.picInQrCode;


/**
 * 二维码里嵌入图片  测试类
 */
public class QrCodeTest {

  public static void main(String[] args){
    test();
  }

  public static void test(){
    GreetingService greetingService = message -> System.out.println(message+"你是猪 " );
    greetingService.sayMessage("pig");
  }

  @FunctionalInterface
  interface GreetingService {
    void sayMessage(String message);
  }

  public static void qrCodeTest() throws Exception {
    // 存放在二维码中的内容
    String text = "吃屎吧你";
    // 嵌入二维码的图片路径
    String imgPath =
            "D:\\qycache\\WeChat Files\\wxid_ooc5fm0cdu8122\\FileStorage\\File\\2020-08\\二维码\\abc.jpg";
    // 生成的二维码的路径及名称
    String destPath =
            "D:\\qycache\\WeChat Files\\wxid_ooc5fm0cdu8122\\FileStorage\\File\\2020-08\\二维码\\二维码嵌入图片.jpg";
    // 生成二维码
    QRCodeUtil.encode(text, imgPath, destPath, true);
    // 解析二维码
    String str = QRCodeUtil.decode(destPath);
    // 打印出解析出的内容
    System.out.println(str);
  }
}