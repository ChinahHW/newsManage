package com.huwei.newsdemo.biz.service;

import com.baomidou.mybatisplus.service.IService;
import com.huwei.newsdemo.biz.entity.Class;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huwei
 * @since 2019-09-03
 */
public interface IClassService extends IService<Class> {

    List<Class> queryAll();

    List<Class> queryByKeyWord();

    String queryTreeMenu();

    boolean delNode(Class newsClass);

    Class queryById(Class clazz);
}
