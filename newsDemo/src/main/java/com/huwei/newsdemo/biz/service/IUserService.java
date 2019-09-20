package com.huwei.newsdemo.biz.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.huwei.newsdemo.biz.entity.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huwei
 * @since 2019-09-03
 */
public interface IUserService extends IService<User> {

    List<User> queryAll(int userId);

    Page<User> queryByKeyWord(String newsKeyWord, int page, int count, int userId);

    Page<User> queryByPage(int page, int count, int userId);

    User toLogin(String userName, String password);

    boolean add(User user, int[] roles);

    boolean update(User user,int[] roles);

    boolean delete(User user);
}
