package com.agriculture.manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.agriculture.manage.biz.controller","com.agriculture.manage.biz.service.impl","com.agriculture.manage.biz.dao","com.agriculture.manage.biz.config"})
@MapperScan(basePackages = "com.agriculture.manage.biz.dao")
public class ManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageApplication.class, args);
    }

}
