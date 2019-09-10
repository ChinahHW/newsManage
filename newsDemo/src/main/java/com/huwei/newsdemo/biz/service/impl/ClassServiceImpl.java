package com.huwei.newsdemo.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.huwei.newsdemo.biz.dao.ClassDao;
import com.huwei.newsdemo.biz.entity.Class;
import com.huwei.newsdemo.biz.service.IClassService;
import com.huwei.newsdemo.response.treeMenu;
import com.huwei.newsdemo.util.GroupTreeUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public List<Class> queryAll() {
        EntityWrapper<Class> wrapper = new EntityWrapper<>();
        wrapper.where("del_flag != {0}", 1);
        wrapper.orderBy("class.order",false);
        return this.selectList(wrapper);
    }

    @Override
    public List<Class> queryByKeyWord() {
        return null;
    }

    @Override
    public String queryTreeMenu() {
        List<Class> newsClasses = queryAll();
        List<treeMenu> treeMenus = new ArrayList<>();
        for (Class newsClass : newsClasses) {
            treeMenu treeMenu = new treeMenu();
            treeMenu.setGroupId(newsClass.getId());
            treeMenu.setGroupName(newsClass.getName());
            treeMenu.setHref("#");
            treeMenu.setParentSeq(newsClass.getParentId()+"");
            treeMenu.setSubFlag(newsClass.getParentId() == null ? "1" : "0");
            treeMenu.setOpenFlag(newsClass.getOpenFlag() == 1 ? true : false);
            treeMenu.setOrder(newsClass.getOrder());
            treeMenus.add(treeMenu);
        }
        GroupTreeUtils treeUtil = new GroupTreeUtils();
        treeMenus = treeUtil.buildGroupTree(treeMenus);
        String groupTreeJson = JSON.toJSONString(treeMenus);
        groupTreeJson = groupTreeJson.replace("groupName", "text");
        groupTreeJson = groupTreeJson.replace("subGroup", "nodes");
        return groupTreeJson;
    }

    @Override
    public boolean delNode(Class newsClass) {
        newsClass.setDelFlag(1);
//        newsClass.setUpdateUserId(2);
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
}
