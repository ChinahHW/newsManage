package com.huwei.newsdemo.biz.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author huwei
 * @since 2019-09-06
 */
@Data
@Accessors(chain = true)
public class Menu extends Model<Menu> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "menu_id", type = IdType.AUTO)
    private Integer menuId;
    @TableField("name")
    private String name;
    /**
     * 前端url
     */
    @TableField("path")
    private String path;
    @TableField("parentId")
    private Integer parentId;
    /**
     * 图标
     */
    @TableField("icon")
    private String icon;
    /**
     * vue页面
     */
    @TableField("component")
    private String component;
    /**
     * 排序值
     */
    @TableField("sort")
    private Integer sort;
    /**
     * 菜单类型，0为菜单，1为按钮
     */
    @TableField("type")
    private Integer type;
    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 0--正常，1--删除
     */
    @TableField("del_flag")
    private Integer delFlag;


    public static final String MENU_ID = "menu_id";

    public static final String NAME = "name";

    public static final String PATH = "path";

    public static final String PARENTID = "parentId";

    public static final String ICON = "icon";

    public static final String COMPONENT = "component";

    public static final String SORT = "sort";

    public static final String TYPE = "type";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String DEL_FLAG = "del_flag";

    @Override
    protected Serializable pkVal() {
        return this.menuId;
    }

}
