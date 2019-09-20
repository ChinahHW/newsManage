package com.huwei.newsdemo.biz.service;

import com.baomidou.mybatisplus.service.IService;
import com.huwei.newsdemo.biz.entity.Dept;
import com.huwei.newsdemo.response.treeMenu;

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

    List<Dept> queryAll(int userId);

    String queryTreeDept(int userId);

    boolean delNode(Dept dept);

    Dept queryById(Dept dept);

    List<treeMenu> queryTreeForList(int userId);

    boolean add(Dept dept,int[] classId, int userId);

    boolean update(Dept dept,int[] classId);

    public List<Dept> querySonDept(Dept dept,List<Dept> deptList);
}
