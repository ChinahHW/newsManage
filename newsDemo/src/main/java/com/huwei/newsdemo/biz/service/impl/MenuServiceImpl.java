package com.huwei.newsdemo.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.huwei.newsdemo.biz.dao.MenuDao;
import com.huwei.newsdemo.biz.entity.Menu;
import com.huwei.newsdemo.biz.entity.Role;
import com.huwei.newsdemo.biz.entity.RoleMenu;
import com.huwei.newsdemo.biz.entity.User;
import com.huwei.newsdemo.biz.service.IMenuService;
import com.huwei.newsdemo.biz.service.IRoleMenuService;
import com.huwei.newsdemo.biz.service.IUserRoleService;
import com.huwei.newsdemo.response.treeMenu;
import com.huwei.newsdemo.util.GroupTreeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements IMenuService {

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IRoleMenuService roleMenuService;

    @Override
    public List<Menu> queryAll() {
        EntityWrapper<Menu> wrapper = new EntityWrapper<>();
        wrapper.where("del_flag != {0}",1);
        wrapper.orderBy("sort",false);
        List<Menu> menuList = this.selectList(wrapper);
        return menuList;
    }

    @Override
    public String queryTreeMenu(User user) {
        List<treeMenu> treeMenus = new ArrayList<>();
        GroupTreeUtils treeUtil = new GroupTreeUtils();
        treeMenus = queryTreeForList(user);
        String groupTreeJson = JSON.toJSONString(treeMenus);
        groupTreeJson = groupTreeJson.replace("groupName", "text");
        groupTreeJson = groupTreeJson.replace("subGroup", "nodes");
        return groupTreeJson;
    }



    @Override
    public boolean delNode(Menu menu) {
        menu.setDelFlag(1);
        this.updateById(menu);
        //删除所有以该节点为父节点的节点
        EntityWrapper<Menu> wrapper = new EntityWrapper<>();
        wrapper.where("parentId = {0}",menu.getMenuId());
        List<Menu> sonMenus = this.selectList(wrapper);
        if(sonMenus != null){
            for (Menu sonMenu : sonMenus) {
                delNode(sonMenu);
            }
        }
        //删除role-menu表中的关联信息
        EntityWrapper<RoleMenu> wrapper1 = new EntityWrapper<>();
        wrapper1.where("menu_id = {0}",menu.getMenuId());
        List<RoleMenu> roleMenuList = roleMenuService.selectList(wrapper1);
        if (roleMenuList != null) {

            for (RoleMenu roleMenu : roleMenuList) {
                roleMenu.deleteById();
            }
        }
        return true;
    }

    @Override
    public Menu queryById(Menu menu) {
        EntityWrapper<Menu> wrapper = new EntityWrapper<>();
        wrapper.where("menu_id = {0}",menu.getMenuId()).and().where("del_flag != {0}",1);
        return this.selectOne(wrapper);
    }

    @Override
    public List<treeMenu> queryTreeForList(User user) {
        List<Menu> menuList = new ArrayList<>();
        if(user.getUserId() != null){
            List<Role> roleList = userRoleService.queryRoleByUserId(user.getUserId());
            if(roleList != null){
                if(roleList.size() != 0){
                    for (Role role : roleList) {
                        List<Menu> menus = roleMenuService.queryMenuByRoleId(role.getRoleId());
                        menuList.addAll(menus);
                    }
                }
            }
        }else{
            menuList = this.queryAll();
        }
        List<treeMenu> treeMenus = new ArrayList<>();
        for (Menu menu : menuList) {
            treeMenu treeMenu = new treeMenu();
            treeMenu.setGroupId(menu.getMenuId());
            treeMenu.setGroupName(menu.getName());
            treeMenu.setHref("#");
            treeMenu.setParentSeq(menu.getParentId()+"");
            treeMenu.setSubFlag(menu.getParentId() == null ? "1" : "0");
            treeMenu.setOrder(menu.getSort());
            treeMenu.setPath(menu.getPath());
            treeMenu.setIcon(menu.getIcon());
            treeMenus.add(treeMenu);
        }
        GroupTreeUtils treeUtil = new GroupTreeUtils();
        treeMenus = treeUtil.buildGroupTree(treeMenus);
        return treeMenus;
    }
}
