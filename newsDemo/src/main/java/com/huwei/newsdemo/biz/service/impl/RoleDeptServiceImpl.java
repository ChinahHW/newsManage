package com.huwei.newsdemo.biz.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.huwei.newsdemo.biz.dao.RoleDeptDao;
import com.huwei.newsdemo.biz.entity.Dept;
import com.huwei.newsdemo.biz.entity.RoleDept;
import com.huwei.newsdemo.biz.service.IRoleDeptService;
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
public class RoleDeptServiceImpl extends ServiceImpl<RoleDeptDao, RoleDept> implements IRoleDeptService {

    @Override
    public List<Dept> queryDeptByRoleId(int roleId) {
        EntityWrapper<RoleDept> wrapper = new EntityWrapper<>();
        wrapper.where("role_id = {0}",roleId);
        List<RoleDept> roleDepts = this.selectList(wrapper);
        Dept dept = new Dept();
        List<Dept> deptList = new ArrayList<>();
        if(roleDepts != null){
            for (RoleDept roleDept : roleDepts) {
                int deptId = roleDept.getDeptId();
                dept.setDeptId(deptId);
                Dept dept1 = dept.selectById();
                deptList.add(dept1);
            }
        }
        return deptList;
    }
}
