package com.huwei.newsdemo.biz.service;

import com.baomidou.mybatisplus.service.IService;
import com.huwei.newsdemo.biz.entity.Class;
import com.huwei.newsdemo.response.treeMenu;

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

    List<Class> queryAll(int userId);

    List<Class> queryByKeyWord();

    String queryTreeMenu(int userId);

    boolean delNode(Class newsClass);

    Class queryById(Class clazz);

    List<treeMenu> queryTreeForList(int userId);
}
