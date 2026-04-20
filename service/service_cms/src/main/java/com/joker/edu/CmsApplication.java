package com.joker.edu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.joker.edu.mapper"})
public class CmsApplication {
  public static void main(String[] args) {
      SpringApplication.run(CmsApplication.class);
  }
}
