package com.huwei.newsdemo.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.huwei.newsdemo.biz.dao.ClassDao;
import com.huwei.newsdemo.biz.entity.Class;
import com.huwei.newsdemo.biz.entity.*;
import com.huwei.newsdemo.biz.service.*;
import com.huwei.newsdemo.response.treeMenu;
import com.huwei.newsdemo.util.GroupTreeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huwei
 * @since 2019-09-03
 */
@Service
public class ClassServiceImpl extends ServiceImpl<ClassDao, Class> implements IClassService {

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IRoleDeptService roleDeptService;

    @Autowired
    private IDeptClassService deptClassService;

    @Autowired
    private INewsClassService newsClassService;

    @Override
    public List<Class> queryAll(int userId) {
        List<Class> classList = new ArrayList<>();
        //通过userId查询出角色，通过角色获得所属部门，通过部门获取新闻分类
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
                        int deptId = roleDept.getDeptId();
                        EntityWrapper<DeptClass> deptClassEntityWrapper = new EntityWrapper<>();
                        deptClassEntityWrapper.where("dept_id = {0}",deptId);
                        List<DeptClass> deptClassList = deptClassService.selectList(deptClassEntityWrapper);
                        if (deptClassList != null) {
                            for (DeptClass deptClass : deptClassList) {
                                int classId = deptClass.getClassId();
                                EntityWrapper<Class> wrapper = new EntityWrapper<>();
                                wrapper.where("del_flag != {0}", 1);
                                wrapper.orderBy("class.order",false);
                                wrapper.where("id = {0}",classId);
                                List<Class> classList1 = this.selectList(wrapper);
                                if (classList1 != null) {
                                    Class clazz = classList1.get(0);
                                    if(!classList.contains(clazz)){
                                        classList.add(clazz);
                                    }
                                    //判断是否为父级，如果为父级，将子级分类添加
                                    List<Class> classList2 = new ArrayList<>();
                                    if(clazz.getParentId() == 0){
                                        classList2 = querySonClass(clazz,classList2);
                                    }
                                    if (classList2 != null) {
                                        for (Class aClass : classList2) {
                                            if(!classList.contains(aClass)){
                                                classList.add(aClass);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Collections.sort(classList);

        return classList;
    }

    @Override
    public List<Class> queryByKeyWord() {
        return null;
    }

    public List<Class> querySonClass(Class clazz,List<Class> classList){
        EntityWrapper<Class> classEntityWrapper = new EntityWrapper<>();
        classEntityWrapper.where("parent_id = {0}",clazz.getId()).and().where("del_flag != {0}", 1);
        List<Class> classList1 = this.selectList(classEntityWrapper);
        if (classList1 != null) {
            for (Class aClass : classList1) {
                if(!classList.contains(aClass)){
                    classList.add(aClass);
                    querySonClass(aClass,classList);
                }
            }
        }
        return classList;
    }

    @Override
    public String queryTreeMenu(int userId) {
        List<treeMenu> treeMenus = new ArrayList<>();
        treeMenus = queryTreeForList(userId);
        String groupTreeJson = JSON.toJSONString(treeMenus);
        groupTreeJson = groupTreeJson.replace("groupName", "text");
        groupTreeJson = groupTreeJson.replace("subGroup", "nodes");
        return groupTreeJson;
    }

    @Override
    public boolean delNode(Class newsClass) {
        //删除deptClass和newsClass表中的信息再删除class表中的信息
        EntityWrapper<DeptClass> deptClassEntityWrapper = new EntityWrapper<>();
        deptClassEntityWrapper.where("class_id = {0}",newsClass.getId());
        List<DeptClass> deptClassList = deptClassService.selectList(deptClassEntityWrapper);
        if (deptClassList != null) {
            for (DeptClass deptClass : deptClassList) {
                deptClass.deleteById();
            }
        }

        EntityWrapper<NewsClass> newsClassEntityWrapper = new EntityWrapper<>();
        newsClassEntityWrapper.where("news_class_id = {0}",newsClass.getId());
        List<NewsClass> newsClassList = newsClassService.selectList(newsClassEntityWrapper);
        if (newsClassList != null) {
            for (NewsClass aClass : newsClassList) {
                aClass.deleteById();
            }
        }
        newsClass.setDelFlag(1);
        this.updateById(newsClass);
        EntityWrapper<Class> wrapper = new EntityWrapper<>();
        wrapper.where("parent_id = {0}",newsClass.getId());
        List<Class> sonList = this.selectList(wrapper);
        if(sonList.size() != 0){
            for (Class sonNode : sonList) {
                delNode(sonNode);
            }
        }
        return true;
    }

    @Override
    public Class queryById(Class clazz) {
        EntityWrapper<Class> wrapper = new EntityWrapper<>();
        wrapper.where("id = {0}",clazz.getId()).and().where("del_flag != {0}",1);
        return this.selectOne(wrapper);
    }

    @Override
    public List<treeMenu> queryTreeForList(int userId) {
        List<Class> classList = queryAll(userId);
        List<treeMenu> treeMenus = new ArrayList<>();
        for (Class clazz : classList) {
            treeMenu treeMenu = new treeMenu();
            treeMenu.setGroupId(clazz.getId());
            treeMenu.setGroupName(clazz.getName());
            treeMenu.setHref("#");
            treeMenu.setParentSeq(clazz.getParentId()+"");
            treeMenu.setSubFlag(clazz.getParentId() == null ? "1" : "0");
            treeMenu.setOpenFlag(clazz.getOpenFlag() == 1 ? true : false);
            treeMenu.setOrder(clazz.getOrder());
            treeMenus.add(treeMenu);
        }
        GroupTreeUtils treeUtil = new GroupTreeUtils();
        if(treeMenus.size() == 1 && !treeMenus.get(0).getParentSeq().equals("0")){
            //说明是非父级的节点
            treeMenus = treeUtil.buildGroupTreeByUserId(treeMenus);
        }else{
            treeMenus = treeUtil.buildGroupTree(treeMenus);
        }
        return treeMenus;
    }
}
