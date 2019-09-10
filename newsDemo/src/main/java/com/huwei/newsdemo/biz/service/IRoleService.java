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

    List<Role> queryAll();

    Page<Role> queryByKeyWord(String keyWord);

    String add(Role role, int[] permission, int[] depts);

    boolean update(Role role, int[] permission, int[] depts);

    Page<Role> queryByPage(int page, int count);

    boolean delete(Role role);
}
