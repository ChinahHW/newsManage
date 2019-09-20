package com.huwei.newsdemo.biz.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.huwei.newsdemo.biz.entity.Role;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huwei
 * @since 2019-09-06
 */
public interface IRoleService extends IService<Role> {

    List<Role> queryAll(int userId);

    Page<Role> queryByKeyWord(String keyWord, int page, int count, int userId);

    String add(Role role, int[] permission, int[] depts);

    boolean update(Role role, int[] permission, int[] depts);

    Page<Role> queryByPage(int page, int count,int userId);

    boolean delete(Role role);
}
