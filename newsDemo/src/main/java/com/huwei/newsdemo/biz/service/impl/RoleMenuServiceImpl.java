package com.huwei.newsdemo.biz.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.huwei.newsdemo.biz.entity.Menu;
import com.huwei.newsdemo.biz.entity.RoleMenu;
import com.huwei.newsdemo.biz.dao.RoleMenuDao;
import com.huwei.newsdemo.biz.service.IRoleMenuService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huwei
 * @since 2019-09-06
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuDao, RoleMenu> implements IRoleMenuService {

    @Override
    public List<Menu> queryMenuByRoleId(int roleId) {
        EntityWrapper<RoleMenu> wrapper = new EntityWrapper<>();
        wrapper.where("role_id = {0}",roleId);
        List<RoleMenu> roleMenus = this.selectList(wrapper);
        Menu menu = new Menu();
        List<Menu> menuList = new ArrayList<>();
        if(roleMenus != null){
            for (RoleMenu roleMenu : roleMenus) {
                int menu_id = roleMenu.getMenuId();
                menu.setMenuId(menu_id);
                Menu menu2 = menu.selectById();
                menuList.add(menu2);
            }
        }
        Collections.sort(menuList);
        return menuList;
    }
}
