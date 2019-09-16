package com.huwei.newsdemo.util;

import com.huwei.newsdemo.response.treeMenu;

import java.util.ArrayList;
import java.util.List;

public class GroupTreeUtils {
    public List<treeMenu> treeMenuList;

    public List<treeMenu> list = new ArrayList<>();

    /**
     *  buildGroupTree：(构建分组树)
     * @author nicky
     * @date   2018年11月7日下午4:34:00
     * @return
     */
    public List<treeMenu> buildGroupTree(List<treeMenu> treeMenuList) {
        this.treeMenuList = treeMenuList;
        for (treeMenu gr : treeMenuList){
            if("0".equals(gr.getParentSeq())) {//根级目录
                treeMenu group = new treeMenu();
                group.setGroupId(gr.getGroupId());
                group.setParentSeq(gr.getParentSeq());
                group.setGroupName(gr.getGroupName());
                group.setGroupDesc(gr.getGroupDesc());
                group.setGroupType(gr.getGroupType());
                group.setHref("#");
                group.setOrder(gr.getOrder());
                group.setOpenFlag(gr.isOpenFlag());
                group.setSubGroup(treeChild(gr.getGroupId()+""));
                group.setPath(gr.getPath());
                group.setIcon(gr.getIcon());
                list.add(group);
            }
        }
        return list;
    }


    public List<treeMenu> buildGroupTreeByUserId(List<treeMenu> treeMenuList) {
        this.treeMenuList = treeMenuList;
        for (treeMenu gr : treeMenuList){
            //无论是否为父级目录，都得查询
            treeMenu group = new treeMenu();
            group.setGroupId(gr.getGroupId());
            group.setParentSeq(gr.getParentSeq());
            group.setGroupName(gr.getGroupName());
            group.setGroupDesc(gr.getGroupDesc());
            group.setGroupType(gr.getGroupType());
            group.setHref("#");
            group.setOrder(gr.getOrder());
            group.setOpenFlag(gr.isOpenFlag());
            group.setSubGroup(treeChild(gr.getGroupId()+""));
            group.setPath(gr.getPath());
            group.setIcon(gr.getIcon());
            list.add(group);
        }
        return list;
    }

    /**
     *  treeChild:(递归遍历子级分组)
     * @author nicky
     * @date   2018年11月7日下午4:33:47
     * @return
     */
    public List<treeMenu> treeChild(String seq){
        List<treeMenu> list = new ArrayList<>();
        for(treeMenu gr : treeMenuList){
            treeMenu group = new treeMenu();
            if(seq.equals(gr.getParentSeq())){
                group.setGroupId(gr.getGroupId());
                group.setParentSeq(gr.getParentSeq());
                group.setGroupName(gr.getGroupName());
                group.setGroupDesc(gr.getGroupDesc());
                group.setGroupType(gr.getGroupType());
                group.setOrder(gr.getOrder());
                group.setOpenFlag(gr.isOpenFlag());
                group.setHref("#");
                group.setSubGroup(treeChild(gr.getGroupId()+""));//递归循环
                group.setPath(gr.getPath());
                group.setIcon(gr.getIcon());
                list.add(group);
            }
        }
        return list;
    }
}
