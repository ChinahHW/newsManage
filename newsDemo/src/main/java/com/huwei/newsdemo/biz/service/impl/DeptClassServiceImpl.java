package com.huwei.newsdemo.biz.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.huwei.newsdemo.biz.entity.Class;
import com.huwei.newsdemo.biz.entity.DeptClass;
import com.huwei.newsdemo.biz.dao.DeptClassDao;
import com.huwei.newsdemo.biz.service.IClassService;
import com.huwei.newsdemo.biz.service.IDeptClassService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huwei
 * @since 2019-09-11
 */
@Service
public class DeptClassServiceImpl extends ServiceImpl<DeptClassDao, DeptClass> implements IDeptClassService {

    @Autowired
    private IClassService classService;

    @Override
    public List<Class> queryClassByDeptId(int deptId) {
        EntityWrapper<DeptClass> wrapper = new EntityWrapper<>();
        wrapper.where("dept_id = {0}",deptId);
        List<DeptClass> deptClassList = this.selectList(wrapper);
        List<Class> classList = new ArrayList<>();
        if(deptClassList != null){
            for (DeptClass deptClass : deptClassList) {
                int classId = deptClass.getClassId();
                Class clazz = classService.selectById(classId);
                classList.add(clazz);
            }
        }
        return classList;
    }
}
