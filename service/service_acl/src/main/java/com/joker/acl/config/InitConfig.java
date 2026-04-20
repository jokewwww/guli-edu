package com.joker.acl.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@ComponentScan("com.joker")
@MapperScan("com.joker.acl.mapper")
@Configuration
public class InitConfig {
}
