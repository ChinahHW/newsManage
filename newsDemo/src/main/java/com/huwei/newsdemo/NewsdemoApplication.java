package com.huwei.newsdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.huwei.newsdemo.biz.controller","com.huwei.newsdemo.biz.service.impl","com.huwei.newsdemo.biz.dao","com.huwei.newsdemo.biz.config"})
@MapperScan(basePackages = "com.huwei.newsdemo.biz.dao")
public class NewsdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsdemoApplication.class, args);
    }

}
