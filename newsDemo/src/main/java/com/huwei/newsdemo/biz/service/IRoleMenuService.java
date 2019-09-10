package com.huwei.newsdemo.biz.service;

import com.baomidou.mybatisplus.service.IService;
import com.huwei.newsdemo.biz.entity.Menu;
import com.huwei.newsdemo.biz.entity.RoleMenu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huwei
 * @since 2019-09-06
 */
public interface IRoleMenuService extends IService<RoleMenu> {

    List<Menu> queryMenuByRoleId(int roleId);
}
