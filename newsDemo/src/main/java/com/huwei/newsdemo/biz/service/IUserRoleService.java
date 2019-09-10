package com.huwei.newsdemo.biz.service;

import com.baomidou.mybatisplus.service.IService;
import com.huwei.newsdemo.biz.entity.Role;
import com.huwei.newsdemo.biz.entity.UserRole;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huwei
 * @since 2019-09-06
 */
public interface IUserRoleService extends IService<UserRole> {

    List<Role> queryRoleByUserId(int userId);
}
