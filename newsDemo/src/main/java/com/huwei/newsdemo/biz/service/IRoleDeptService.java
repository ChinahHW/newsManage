package com.huwei.newsdemo.biz.service;

import com.baomidou.mybatisplus.service.IService;
import com.huwei.newsdemo.biz.entity.Dept;
import com.huwei.newsdemo.biz.entity.RoleDept;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huwei
 * @since 2019-09-06
 */
public interface IRoleDeptService extends IService<RoleDept> {
    List<Dept> queryDeptByRoleId(int roleId);
}
