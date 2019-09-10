package com.huwei.newsdemo.biz.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.huwei.newsdemo.biz.dao.UserRoleDao;
import com.huwei.newsdemo.biz.entity.Role;
import com.huwei.newsdemo.biz.entity.UserRole;
import com.huwei.newsdemo.biz.service.IUserRoleService;
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
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRole> implements IUserRoleService {

    @Override
    public List<Role> queryRoleByUserId(int userId) {
        EntityWrapper<UserRole> wrapper = new EntityWrapper<>();
        wrapper.where("user_id = {0}",userId);
        List<UserRole> userRoles = this.selectList(wrapper);
        Role role = new Role();
        List<Role> roleList = new ArrayList<>();
        if(userRoles != null){
            for (UserRole userRole : userRoles) {
                int roleId = userRole.getRoleId();
                role.setRoleId(roleId);
                EntityWrapper<Role> wrapper1 = new EntityWrapper<>();
                wrapper1.where("role_id = {0}",role.getRoleId()).and().where("del_flag != {0}",1);
                Role role2 = role.selectOne(wrapper1);
                roleList.add(role2);
            }
        }
        return roleList;
    }
}
