package com.huwei.newsdemo.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.huwei.newsdemo.biz.dao.DeptDao;
import com.huwei.newsdemo.biz.entity.Dept;
import com.huwei.newsdemo.biz.entity.DeptClass;
import com.huwei.newsdemo.biz.entity.RoleDept;
import com.huwei.newsdemo.biz.entity.UserRole;
import com.huwei.newsdemo.biz.service.IDeptClassService;
import com.huwei.newsdemo.biz.service.IDeptService;
import com.huwei.newsdemo.biz.service.IRoleDeptService;
import com.huwei.newsdemo.biz.service.IUserRoleService;
import com.huwei.newsdemo.response.treeMenu;
import com.huwei.newsdemo.util.GroupTreeUtils;
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
 * @since 2019-09-06
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptDao, Dept> implements IDeptService {

    @Autowired
    private IRoleDeptService roleDeptService;

    @Autowired
    private IDeptClassService deptClassService;

    @Autowired
    private IUserRoleService userRoleService;

    @Override
    public List<Dept> queryAll(int userId) {

        List<Dept> deptList = new ArrayList<>();
        //通过userId查询出角色，通过角色获得所属部门
        EntityWrapper<UserRole> userRoleWrapper = new EntityWrapper<>();
        userRoleWrapper.where("user_id = {0}",userId);
        List<UserRole> userRoleList = userRoleService.selectList(userRoleWrapper);
        if(userRoleList != null){
            for (UserRole userRole : userRoleList) {
                int roleId = userRole.getRoleId();
                EntityWrapper<RoleDept> roleDeptEntityWrapper = new EntityWrapper<>();
                roleDeptEntityWrapper.where("role_id = {0}",roleId);
                List<RoleDept> roleDeptList = roleDeptService.selectList(roleDeptEntityWrapper);
                if (roleDeptList != null) {
                    for (RoleDept roleDept : roleDeptList) {
                        EntityWrapper<Dept> wrapper = new EntityWrapper<>();
                        wrapper.where("del_flag != {0}",1).and().where("dept_id = {0}",roleDept.getDeptId());
                        wrapper.orderBy("sort",false);
                        List<Dept> deptList1 = this.selectList(wrapper);
                        if (deptList1 != null) {
                            for (Dept dept : deptList1) {
                                if(!deptList.contains(dept)){
                                    deptList.add(dept);
                                }
                                //判断是否为父级，如果为父级，将子级分类添加
                                List<Dept> deptList2 = new ArrayList<>();
                                if(dept.getParentId() == 0){
                                    deptList2 = querySonDept(dept,deptList2);
                                }
                                if (deptList2 != null) {
                                    for (Dept dept1 : deptList2) {
                                        if(!deptList.contains(dept1)){
                                            deptList.add(dept1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return deptList;
    }

    public List<Dept> querySonDept(Dept dept,List<Dept> deptList){
        EntityWrapper<Dept> deptEntityWrapper = new EntityWrapper<>();
        deptEntityWrapper.where("parent_id = {0}",dept.getDeptId()).and().where("del_flag != {0}", 1);
        List<Dept> deptList1 = this.selectList(deptEntityWrapper);
        if (deptList1 != null) {
            for (Dept dept1 : deptList1) {
                if(!deptList.contains(dept1)){
                    deptList.add(dept1);
                    querySonDept(dept1,deptList);
                }
            }
        }
        return deptList;
    }

    @Override
    public String queryTreeDept(int userId) {
        List<treeMenu> treeDepts = new ArrayList<>();

        treeDepts = queryTreeForList(userId);
        String groupTreeJson = JSON.toJSONString(treeDepts);
        groupTreeJson = groupTreeJson.replace("groupName", "text");
        groupTreeJson = groupTreeJson.replace("subGroup", "nodes");
        return groupTreeJson;
    }

    @Override
    public boolean delNode(Dept dept) {
        dept.setDelFlag(1);
        this.updateById(dept);
        //删除所有以该节点为父节点的节点
        EntityWrapper<Dept> wrapper = new EntityWrapper<>();
        wrapper.where("parent_id = {0}",dept.getDeptId());
        List<Dept> sonDepts = this.selectList(wrapper);
        if(sonDepts != null){
            for (Dept sonDept : sonDepts) {
                delNode(sonDept);
            }
        }

        //删除role-dept表中的关联信息
        EntityWrapper<RoleDept> wrapper1 = new EntityWrapper<>();
        wrapper1.where("dept_id = {0}",dept.getDeptId());
        List<RoleDept> roleDeptList = roleDeptService.selectList(wrapper1);
        if (roleDeptList != null) {

            for (RoleDept roleDept : roleDeptList) {
                roleDept.deleteById();
            }
        }
        //删除dept-class表中的关联信息
        EntityWrapper<DeptClass> wrapper2 = new EntityWrapper<>();
        wrapper2.where("dept_id = {0}",dept.getDeptId());
        List<DeptClass> deptClassList = deptClassService.selectList(wrapper2);
        if (deptClassList != null) {
            for (DeptClass deptClass : deptClassList) {
                deptClass.deleteById();
            }
        }
        return true;
    }

    @Override
    public Dept queryById(Dept dept) {
        EntityWrapper<Dept> wrapper = new EntityWrapper<>();
        wrapper.where("dept_id = {0}",dept.getDeptId()).and().where("del_flag != {0}",1);
        return this.selectOne(wrapper);
    }

    @Override
    public List<treeMenu> queryTreeForList(int userId) {
        List<treeMenu> treeDepts = new ArrayList<>();

        List<Dept> deptList = queryAll(userId);

        for (Dept dept : deptList) {
            treeMenu treeDept = new treeMenu();
            treeDept.setGroupId(dept.getDeptId());
            treeDept.setGroupName(dept.getDeptName());
            treeDept.setHref("#");
            treeDept.setParentSeq(dept.getParentId()+"");
            treeDept.setSubFlag(dept.getParentId() == null ? "1" : "0");
            treeDept.setOrder(dept.getSort());
            treeDepts.add(treeDept);
        }
        GroupTreeUtils treeUtil = new GroupTreeUtils();
        if(treeDepts.size() == 1 && !treeDepts.get(0).getParentSeq().equals("0")){
            //说明是非父级的节点
            treeDepts = treeUtil.buildGroupTreeByUserId(treeDepts);
        }else{
            treeDepts = treeUtil.buildGroupTree(treeDepts);
        }
        return treeDepts;
    }

    @Override
    public boolean add(Dept dept, int[] classId, int userId) {
        EntityWrapper<Dept> qryWrapper = new EntityWrapper<>();
        qryWrapper.where("dept_name = {0}",dept.getDeptName()).and().where("del_flag != {0}",1);
        Dept dept1 = dept.selectOne(qryWrapper);
        if(dept1 != null){
            return false;
        }
        this.insert(dept);
        if (classId != null) {
            for (int clazzId : classId) {
                DeptClass deptClass = new DeptClass();
                deptClass.setClassId(clazzId);
                deptClass.setDeptId(dept.getDeptId());
                deptClass.insert();
            }
        }
        //将当前新增的dept关联到当前角色下
        EntityWrapper<UserRole> userRoleEntityWrapper = new EntityWrapper<>();
        userRoleEntityWrapper.where("user_id = {0}",userId);
        List<UserRole> userRoleList = userRoleService.selectList(userRoleEntityWrapper);
        if (userRoleList != null) {
            for (UserRole userRole : userRoleList) {
                int roleId = userRole.getRoleId();
                RoleDept roleDept = new RoleDept();
                roleDept.setDeptId(dept.getDeptId());
                roleDept.setRoleId(roleId);
                roleDeptService.insert(roleDept);
            }
        }
        return true;
    }

    @Override
    public boolean update(Dept dept, int[] classId) {
        if(classId == null){
            dept.updateById();
        }else{
            dept.updateById();

            //更改相关的dept-class表中的信息，删除原本的记录，增加新的记录
            EntityWrapper<DeptClass> wrapper = new EntityWrapper<>();
            wrapper.where("dept_id = {0}",dept.getDeptId());
            List<DeptClass> deptClassList = deptClassService.selectList(wrapper);
            if(deptClassList != null){
                for (DeptClass deptClass : deptClassList) {
                    deptClass.deleteById();
                }
            }
            for (int clazzId : classId) {
                DeptClass deptClass = new DeptClass();
                deptClass.setDeptId(dept.getDeptId());
                deptClass.setClassId(clazzId);
                deptClass.insert();
            }
        }
        return true;
    }
}
