package com.huwei.newsdemo.biz.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.huwei.newsdemo.biz.dao.RoleDao;
import com.huwei.newsdemo.biz.entity.*;
import com.huwei.newsdemo.biz.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements IRoleService {

    @Autowired
    private IRoleDeptService roleDeptService;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IRoleMenuService roleMenuService;

    @Autowired
    private IDeptService deptService;

    @Override
    public List<Role> queryAll(int userId) {
        List<Role> roleList = new ArrayList<>();
        List<Dept> deptList = new ArrayList<>();
        //通过userid查询出角色，通过角色获得部门及其子部门，通过部门获取部门下所有角色
        EntityWrapper<UserRole> userRoleEntityWrapper = new EntityWrapper<>();
        userRoleEntityWrapper.where("user_id = {0}",userId);
        List<UserRole> userRoleList = userRoleService.selectList(userRoleEntityWrapper);
        if (userRoleList != null) {
            for (UserRole userRole : userRoleList) {

                //添加本身的角色
                Role role = new Role();
                role = role.selectById(userRole.getRoleId());
                if(!roleList.contains(role)){
                    roleList.add(role);
                }


                EntityWrapper<RoleDept> roleDeptEntityWrapper = new EntityWrapper<>();
                roleDeptEntityWrapper.where("role_id = {0}",userRole.getRoleId());
                List<RoleDept> roleDeptList = roleDeptService.selectList(roleDeptEntityWrapper);
                if (roleDeptList != null) {
                    for (RoleDept roleDept : roleDeptList) {
                        EntityWrapper<Dept> deptEntityWrapper = new EntityWrapper<>();
                        deptEntityWrapper.where("dept_id = {0}",roleDept.getDeptId());
                        List<Dept> deptList1 = deptService.selectList(deptEntityWrapper);
                        if (deptList1 != null) {
                            for (Dept dept : deptList1) {
//                                if(!deptList.contains(dept)){
//                                    deptList.add(dept);
//                                }
                                //判断是否为父级，如果为父级，将子级分类添加
                                List<Dept> deptList2 = new ArrayList<>();
                                EntityWrapper<Dept> deptEntityWrapper1 = new EntityWrapper<>();
                                deptEntityWrapper1.where("parent_id = {0}",dept.getDeptId());
                                if (deptService.selectList(deptEntityWrapper1) != null) {
                                    deptList2 = deptService.querySonDept(dept,deptList2);
                                }
//                                if(dept.getParentId() == 0){
//                                    deptList2 = deptService.querySonDept(dept,deptList2);
//                                }
                                if (deptList2 != null) {
                                    for (Dept dept1 : deptList2) {
                                        if(!deptList.contains(dept1)){
                                            deptList.add(dept1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }

        //已经获取了子部门,获取这些部门下的角色
        for (Dept dept : deptList) {
            EntityWrapper<RoleDept> roleDeptEntityWrapper = new EntityWrapper<>();
            roleDeptEntityWrapper.where("dept_id = {0}",dept.getDeptId());
            List<RoleDept> roleDeptList = roleDeptService.selectList(roleDeptEntityWrapper);
            if (roleDeptList != null) {
                for (RoleDept roleDept : roleDeptList) {
                    EntityWrapper<Role> wrapper = new EntityWrapper<>();
                    wrapper.where("del_flag != {0}",1).and().where("role_id = {0}",roleDept.getRoleId());
                    List<Role> roles = this.selectList(wrapper);
                    if (roles != null) {
                        for (Role role : roles) {
                            if(!roleList.contains(role)){
                                roleList.add(role);
                            }
                        }
                    }
                }
            }

        }
        return roleList;
    }

    @Override
    public Page<Role> queryByKeyWord(String keyWord, int page, int count,int userId) {
        Page<Role> rolePage = new Page<>();
        List<Role> roleList = queryAll(userId);
        List<Role> keyWordRoleList = new ArrayList<>();
        if (roleList != null) {
            for (Role role : roleList) {
                if(role.getRoleName().contains(keyWord) || role.getRoleDesc().contains(keyWord)) {
                    if(keyWordRoleList.size() < 10){
                        keyWordRoleList.add(role);
                    }
                }
            }
        }
        Collections.sort(keyWordRoleList);
        List<Role> roleList1 = new ArrayList<>();
        if (page * count > keyWordRoleList.size()) {
            roleList1 = keyWordRoleList.subList((page - 1) * count,keyWordRoleList.size());
        }else{
            roleList1 = keyWordRoleList.subList((page - 1) * count,(page - 1) * count + count);
        }
        rolePage.setRecords(roleList1);
        rolePage.setTotal(keyWordRoleList.size());
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
    public Page<Role> queryByPage(int page, int count,int userId) {
        Page<Role> rolePage = new Page<>(page,count);
        List<Role> roleList = queryAll(userId);
        List<Role> roleList1 = new ArrayList<>();
        if (page * count > roleList.size()) {
            roleList1 = roleList.subList((page - 1) * count,roleList.size());
        }else{
            roleList1 = roleList.subList((page - 1) * count,(page - 1) * count + count);
        }

        rolePage.setRecords(roleList1);
        rolePage.setTotal(roleList.size());
        return rolePage;
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
