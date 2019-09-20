package com.huwei.newsdemo.biz.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String path = this.getClass().getClassLoader().getResource(".").getPath().replace("/target/classes/","/uploadImage/");
        String staticPath = this.getClass().getClassLoader().getResource(".").getPath().replace("/target/classes/","/staticHtmlPath/");
        System.out.println("--------"+path);
        registry.addResourceHandler("/file/**").addResourceLocations("file:" + path).addResourceLocations("file:"+staticPath);
    }
}
