package com.huwei.newsdemo.biz.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.huwei.newsdemo.biz.dao.UserDao;
import com.huwei.newsdemo.biz.entity.User;
import com.huwei.newsdemo.biz.entity.UserRole;
import com.huwei.newsdemo.biz.service.IUserService;
import org.springframework.stereotype.Service;

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

    @Override
    public List<User> queryAll() {
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.where("del_flag != {0}",1);
        return this.selectList(wrapper);
    }

    @Override
    public Page<User> queryByKeyWord(String newsKeyWord, int page, int count) {
        EntityWrapper<User> qryWrapper = new EntityWrapper<>();
        qryWrapper.like("user_name",newsKeyWord).and().where("del_flag != {0}",1);
        System.out.println(qryWrapper);
        Page<User> userPage = new Page<>(page,count);
        userPage = this.selectPage(userPage,qryWrapper);
        return userPage;
    }

    @Override
    public Page<User> queryByPage(int page, int count) {
        Page<User> userPage = new Page<>(page,count);
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.where("del_flag != {0}",1);
        return this.selectPage(userPage,wrapper);
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
