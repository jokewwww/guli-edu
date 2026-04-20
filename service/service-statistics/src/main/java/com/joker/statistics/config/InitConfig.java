package com.joker.statistics.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = {"com.joker"})
@MapperScan(basePackages = "com.joker.statistics.mapper")
@Configuration
public class InitConfig {
}
