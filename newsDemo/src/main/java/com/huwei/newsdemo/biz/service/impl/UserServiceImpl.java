package com.huwei.newsdemo.biz.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.huwei.newsdemo.biz.dao.UserDao;
import com.huwei.newsdemo.biz.entity.*;
import com.huwei.newsdemo.biz.service.*;
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
 * @since 2019-09-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements IUserService {

    @Autowired
    private IRoleDeptService roleDeptService;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IDeptService deptService;

    @Autowired
    private IRoleService roleService;

    @Override
    public List<User> queryAll(int userId) {
        List<User> userList = new ArrayList<>();
        List<Dept> deptList = new ArrayList<>();
        List<Role> roleList = new ArrayList<>();

        //添加本身的用户
        User user = new User();
        user = user.selectById(userId);
        if(!userList.contains(user)){
            userList.add(user);
        }

        //通过userid查询出角色，通过角色获得部门及其子部门，通过部门获取部门下所有角色,再获取这些角色对应的用户
        EntityWrapper<UserRole> userRoleEntityWrapper = new EntityWrapper<>();
        userRoleEntityWrapper.where("user_id = {0}",userId);
        List<UserRole> userRoleList = userRoleService.selectList(userRoleEntityWrapper);
        if (userRoleList != null) {
            for (UserRole userRole : userRoleList) {
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

        //已经获取了部门和其子部门,获取这些部门下的角色
        for (Dept dept : deptList) {
            EntityWrapper<RoleDept> roleDeptEntityWrapper = new EntityWrapper<>();
            roleDeptEntityWrapper.where("dept_id = {0}",dept.getDeptId());
            List<RoleDept> roleDeptList = roleDeptService.selectList(roleDeptEntityWrapper);
            if (roleDeptList != null) {
                for (RoleDept roleDept : roleDeptList) {
                    EntityWrapper<Role> wrapper = new EntityWrapper<>();
                    wrapper.where("del_flag != {0}",1).and().where("role_id = {0}",roleDept.getRoleId());
                    List<Role> roles = roleService.selectList(wrapper);
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
        if (roleList != null) {
            //已经获得了所有的角色，再获取这些角色对应的用户
            for (Role role : roleList) {
                EntityWrapper<UserRole> userRoleEntityWrapper1 = new EntityWrapper<>();
                userRoleEntityWrapper1.where("role_id = {0}",role.getRoleId());
                List<UserRole> userRoleList1 = userRoleService.selectList(userRoleEntityWrapper1);
                if (userRoleList1 != null) {
                    for (UserRole userRole : userRoleList1) {
                        EntityWrapper<User> userEntityWrapper = new EntityWrapper<>();
                        userEntityWrapper.where("userId = {0}",userRole.getUserId());
                        List<User> userList1 = selectList(userEntityWrapper);
                        if (userList1 != null) {
                            for (User user2 : userList1) {
                                if(!userList.contains(user2)){
                                    userList.add(user2);
                                }
                            }
                        }
                    }
                }
            }
        }
        return userList;
    }

    @Override
    public Page<User> queryByKeyWord(String newsKeyWord, int page, int count,int userId) {
        Page<User> userPage = new Page<>(page,count);
        List<User> userList = queryAll(userId);
        List<User> keyWordUserList = new ArrayList<>();
        if (userList != null) {
            for (User user : userList) {
                if(user.getUserName().contains(newsKeyWord) || user.getNote().contains(newsKeyWord)) {
                    if(keyWordUserList.size() < 10){
                        keyWordUserList.add(user);
                    }
                }
            }
        }
        List<User> userList1 = new ArrayList<>();
        if (page * count > keyWordUserList.size()) {
            userList1 = keyWordUserList.subList((page - 1) * count,keyWordUserList.size());
        }else{
            userList1 = keyWordUserList.subList((page - 1) * count,(page - 1) * count + count);
        }
        userPage.setRecords(userList1);
        userPage.setTotal(keyWordUserList.size());
        return userPage;
    }

    @Override
    public Page<User> queryByPage(int page, int count, int userId) {
        Page<User> userPage = new Page<>(page,count);
        List<User> userList = queryAll(userId);
        List<User> userList1 = new ArrayList<>();
        if (page * count > userList.size()) {
            userList1 = userList.subList((page - 1) * count,userList.size());
        }else{
            userList1 = userList.subList((page - 1) * count,(page - 1) * count + count);
        }

        userPage.setRecords(userList1);
        userPage.setTotal(userList.size());
        return userPage;
    }

    @Override
    public User toLogin(String userName, String password) {
        EntityWrapper<User> qryWrapper = new EntityWrapper<>();
        qryWrapper.where("user_name = {0}",userName).and().where("password = {0}",password).and().where("del_flag != {0}",1);
        List<User> userList = this.selectList(qryWrapper);
        return userList.size() > 0 ? userList.get(0) : null;
    }

    @Override
    public boolean add(User user, int[] roles) {
        EntityWrapper<User> qryWrapper = new EntityWrapper<>();
        qryWrapper.where("user_name = {0}",user.getUserName()).and().where("del_flag != {0}",1);
        User user2 = user.selectOne(qryWrapper);
        if(user2 != null){
            return false;
        }
        this.insert(user);
        if (roles != null) {
            for (int roleId : roles) {
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getUserId());
                userRole.setRoleId(roleId);
                userRole.insert();
            }
        }
        return true;
    }

    @Override
    public boolean update(User user, int[] roles) {
        if(roles == null){
            //为空，则只修改role的信息，不修改关联表的信息
            user.updateById();
        }else{
            //不为空，则需要修改user的信息和关联表user-role的信息
            user.updateById();
            UserRole userRole = new UserRole();
            EntityWrapper<UserRole> wrapper = new EntityWrapper<>();
            wrapper.where("user_id = {0}",user.getUserId()).and().where("del_flag != {0}",1);
            List<UserRole> userRoleList = userRole.selectList(wrapper);
            if(userRoleList != null){
                //删除user-role表中的旧记录，将新的记录插入
                for (UserRole userRole1 : userRoleList) {
                    userRole1.deleteById();
                }
            }
            for (int roleId : roles) {
                userRole.setRoleId(roleId);
                userRole.setUserId(user.getUserId());
                userRole.insert();
            }

        }
        return true;
    }

    @Override
    public boolean delete(User user) {
        //删除userRole表中的记录，再删除user表中的记录
        UserRole userRole = new UserRole();
        EntityWrapper<UserRole> wrapper = new EntityWrapper<>();
        wrapper.where("user_id = {0}",user.getUserId());
        List<UserRole> userRoleList = userRole.selectList(wrapper);
        if (userRoleList != null) {
            for (UserRole userRole1 : userRoleList) {
                userRole1.deleteById();
            }
        }
        user.setDelFlag(1);
        return user.updateById();
    }
}
