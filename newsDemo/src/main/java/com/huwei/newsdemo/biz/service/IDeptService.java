package com.huwei.newsdemo.biz.service;

import com.baomidou.mybatisplus.service.IService;
import com.huwei.newsdemo.biz.entity.Dept;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huwei
 * @since 2019-09-06
 */
public interface IDeptService extends IService<Dept> {

    List<Dept> queryAll();

    String queryTreeDept();

    boolean delNode(Dept dept);

    Dept queryById(Dept dept);
}
