package com.huwei.newsdemo.biz.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.huwei.newsdemo.biz.dao.RoleDao;
import com.huwei.newsdemo.biz.entity.Role;
import com.huwei.newsdemo.biz.entity.RoleDept;
import com.huwei.newsdemo.biz.entity.RoleMenu;
import com.huwei.newsdemo.biz.entity.UserRole;
import com.huwei.newsdemo.biz.service.IRoleDeptService;
import com.huwei.newsdemo.biz.service.IRoleMenuService;
import com.huwei.newsdemo.biz.service.IRoleService;
import com.huwei.newsdemo.biz.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements IRoleService {

    @Autowired
    private IRoleDeptService roleDeptService;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IRoleMenuService roleMenuService;

    @Override
    public List<Role> queryAll() {
        EntityWrapper<Role> wrapper = new EntityWrapper<>();
        wrapper.where("del_flag != {0}",1);
        List<Role> roles = this.selectList(wrapper);
        return roles;
    }

    @Override
    public Page<Role> queryByKeyWord(String keyWord) {
        EntityWrapper<Role> wrapper = new EntityWrapper<>();
        wrapper.where("del_flag != {0}",1).and().like("role_name",keyWord).or().like("role_code",keyWord).or().like("role_desc",keyWord);
        Page<Role> rolePage = new Page<>(1,7);
        rolePage = this.selectPage(rolePage,wrapper);
        return rolePage;
    }

    @Override
    @Transactional
    public String add(Role role, int[] permission, int[] depts) {
        //添加用户之前先查询是否已经存在该用户
        EntityWrapper<Role> wrapper = new EntityWrapper<>();
        wrapper.where("role_name = {0}",role.getRoleName()).and().where("del_flag != {0}",1);
        List<Role> roles = this.selectList(wrapper);
        if(roles.size() != 0){
            return "用户已存在，请勿重复添加！！";
        }
        role.insert();
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setRoleId(role.getRoleId());
        for (int menuId : permission) {
            roleMenu.setMenuId(menuId);
            roleMenu.insert();
        }

        RoleDept roleDept = new RoleDept();
        roleDept.setRoleId(role.getRoleId());
        for (int deptId : depts) {
            roleDept.setDeptId(deptId);
            roleDept.insert();
        }
        return "success";
    }

    @Override
    public boolean update(Role role, int[] permission, int[] depts) {
        if(permission == null && depts == null){
            //为空，则只修改role的信息，不修改关联表的信息
            role.updateById();
        }else{
            //不为空，则需要修改role的信息和关联表role-menu的信息和role-dept的信息
            role.updateById();
            if(permission != null){
                RoleMenu roleMenu = new RoleMenu();
                EntityWrapper<RoleMenu> wrapper = new EntityWrapper<>();
                wrapper.where("role_id = {0}",role.getRoleId());
                List<RoleMenu> roleMenus = roleMenu.selectList(wrapper);
                if(roleMenus != null){
                    //删除原本的rolemenu信息，添加新的关联信息
                    for (RoleMenu roleMenu1 : roleMenus) {
                        roleMenu1.deleteById();
                    }
                }
                for (int menuId : permission) {
                    roleMenu.setMenuId(menuId);
                    roleMenu.setRoleId(role.getRoleId());
                    roleMenu.insert();
                }
            }

            if(depts != null){
                EntityWrapper<RoleDept> wrapperRoleDept = new EntityWrapper<>();
                wrapperRoleDept.where("role_id = {0}",role.getRoleId());
                List<RoleDept> roleDepts = roleDeptService.selectList(wrapperRoleDept);
                if (roleDepts != null) {
                    for(RoleDept roleDept : roleDepts){
                        roleDept.deleteById();
                    }
                }
                for(int deptId : depts) {
                    RoleDept roleDept = new RoleDept();
                    roleDept.setDeptId(deptId);
                    roleDept.setRoleId(role.getRoleId());
                    roleDeptService.insert(roleDept);
                }
            }
        }
        return true;
    }

    @Override
    public Page<Role> queryByPage(int page, int count) {
        Page<Role> rolePage = new Page<>(page,count);
        EntityWrapper<Role> wrapper = new EntityWrapper<>();
        wrapper.where("del_flag != {0}",1);
        return this.selectPage(rolePage,wrapper);
    }

    @Override
    public boolean delete(Role role) {
        //删除roleMenu,role-dept,user-role表中的信息，再删除role表中的信息
        RoleMenu roleMenu = new RoleMenu();
        EntityWrapper<RoleMenu> wrapper = new EntityWrapper<>();
        wrapper.where("role_id = {0}",role.getRoleId());
        List<RoleMenu> roleMenus = roleMenu.selectList(wrapper);
        if(roleMenus != null){
            for (RoleMenu roleMenu1 : roleMenus) {
                roleMenu1.deleteById();
            }
        }

        EntityWrapper<RoleDept> wrapperRoleDept = new EntityWrapper<>();
        wrapperRoleDept.where("role_id = {0}",role.getRoleId());
        List<RoleDept> roleDeptList = roleDeptService.selectList(wrapperRoleDept);
        if (roleDeptList != null) {
            for (RoleDept roleDept : roleDeptList) {
                roleDept.deleteById();
            }
        }

        EntityWrapper<UserRole> wrapperUserRole = new EntityWrapper<>();
        wrapperUserRole.where("role_id = {0}",role.getRoleId());
        List<UserRole> userRoleList = userRoleService.selectList(wrapperUserRole);
        if (userRoleList != null) {
            for (UserRole userRole : userRoleList) {
                userRole.deleteById();
            }
        }
        role.setDelFlag(1);
        return role.updateById();
    }
}
