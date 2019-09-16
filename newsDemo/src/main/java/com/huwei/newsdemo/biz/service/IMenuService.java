package com.huwei.newsdemo.biz.service;

import com.baomidou.mybatisplus.service.IService;
import com.huwei.newsdemo.biz.entity.Menu;
import com.huwei.newsdemo.biz.entity.User;
import com.huwei.newsdemo.response.treeMenu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huwei
 * @since 2019-09-06
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> queryAll();

    String queryTreeMenu(User user);

    boolean delNode(Menu menu);

    Menu queryById(Menu menu);

    List<treeMenu> queryTreeForList(User user);
}
