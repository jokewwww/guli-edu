package com.joker.order.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.joker"})
@MapperScan(basePackages = "com.joker.order.mapper")
public class InitConfig {
}
