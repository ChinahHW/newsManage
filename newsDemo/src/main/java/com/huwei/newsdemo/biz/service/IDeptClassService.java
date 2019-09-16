package com.huwei.newsdemo.biz.service;

import com.baomidou.mybatisplus.service.IService;
import com.huwei.newsdemo.biz.entity.Class;
import com.huwei.newsdemo.biz.entity.DeptClass;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huwei
 * @since 2019-09-11
 */
public interface IDeptClassService extends IService<DeptClass> {

    List<Class> queryClassByDeptId(int deptId);

}
