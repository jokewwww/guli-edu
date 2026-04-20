package com.joker.edu;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class EduServiceApplication {

  public static void main(String[] args) {
      long timeMillis = System.currentTimeMillis();
      SpringApplication.run(EduServiceApplication.class);
      long endTimeMills = System.currentTimeMillis();
      System.out.println("启动时间："+(endTimeMills-timeMillis));
  }
}
