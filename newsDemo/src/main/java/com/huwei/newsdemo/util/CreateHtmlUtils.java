package com.huwei.newsdemo.util;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.Map;

public class CreateHtmlUtils {
    /**
     * 通过freemarker生成静态HTML页面
     * @param ftlName                       模版名称
     * @param targetFileName        模版生成后的文件名
     * @param map                           freemarker生成的数据都存储在MAP中，
     * @创建时间：2017年10月22日21:41:06
     */
    public static void createHtml(String templateName, String targetFileName, Map<String, Object> map) throws Exception{
        System.out.println("---" + targetFileName);
        //创建fm的配置
        Configuration config = new Configuration();
        //指定默认编码格式
        config.setDefaultEncoding("UTF-8");
        //设置模版文件的路径
//        config.setClassForTemplateLoading(CreateHtmlUtils.class, "/com/huwei/newsdemo/biz/config");
        //②读取模板文件夹
        config.setDirectoryForTemplateLoading(new File("F:\\IdeaWorkSpace\\newsDemo\\src\\main\\java\\com\\huwei\\newsdemo\\biz\\config"));//设置要加载的模板文件的路径
        //获得模版包
        Template template = config.getTemplate(templateName);
        //从参数文件中获取指定输出路径 ,路径示例：C:/Workspace/shop-test/src/main/webapp/html

        //定义输出流，注意必须指定编码
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFileName),"UTF-8"));
        //生成模版
        template.process(map, writer);
    }
}
