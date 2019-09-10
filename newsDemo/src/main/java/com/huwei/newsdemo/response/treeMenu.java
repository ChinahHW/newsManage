package com.huwei.newsdemo.response;

import lombok.Data;

import java.util.List;

/**
 * 新闻类别树
 */
@Data
public class treeMenu {

    private int groupId;

    private String groupName;//分组名称

    private String groupDesc;//分组描述

    private Long groupType;//分组的类型（暂时只对true_false表进行分组，1：truefalse表）

    private String parentSeq;//父分组主键

    private String href;//链接,格式为：#href

    private String subFlag;//判断一个树是否有子级节点

    private String state;//树表格是否展开属性

    private List<treeMenu> subGroup;//子级分组列表

    private int order;//排序规则，越大越靠前

    private boolean openFlag;//判断是否可见

    private String path;//点击事件跳转的链接

    private String icon;//按钮的图片
}
