package com.joker.commonutils.cfca;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CFCASignature {

  // 例如:CFCASignature. signature ("/app/etc/product/ 888888-汇付测试商户.pfx", “888888”,
  // base64RequestParams, "utf-8");

  /**
   * cfca 数据签名
   *
   * @param pfxFilePath 商户pfx密钥文件, 包含路径和文件名
   * @param pfxFilePwd 商户pfx密钥文件密码 888888
   * @param content 被加密的数据体
   * @param charset 字符编码
   * @return
   */
  public static SignResult signature(
      String pfxFilePath, String pfxFilePwd, String content, String charset) {

    return null;
  }

  // 例如：CFCASignature. verifyChinaPnRSign ("100001", sign, "utf-8", "/app/etc/product/
  // CFCA_TEST_OCA1.cer");

  /**
   * cfca 验证签名
   *
   * @param huifuId 商户号，此处为汇付的商户号100001
   * @param signature 签名密文
   * @param charset 字符编码
   * @param trustCerPath 商户cer验签文件, 包含路径和文件名
   * @return
   */
  public static VerifyResult verifyChinaPnRSign(
      String huifuId, String signature, String charset, String trustCerPath) {
return null;
  }

  public static void main(String[] args) {
    String yyyyMMdd24Miss = new SimpleDateFormat("YYYYMMDDHH24M").format(new Date());
    System.out.println(yyyyMMdd24Miss);
  }
}
