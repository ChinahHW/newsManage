package com.huwei.newsdemo.biz.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author huwei
 * @since 2019-09-17
 */
@Data
@Accessors(chain = true)
public class Advertising extends Model<Advertising> implements Comparable<Advertising>{

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 广告编码
     */
    @TableField("code")
    private Integer code;
    @TableField("name")
    private String name;
    @TableField("img")
    private String img;
    /**
     * 简介
     */
    @TableField("simple_description")
    private String simpleDescription;
    /**
     * 详细介绍
     */
    @TableField("description")
    private String description;
    /**
     * 广告标签
     */
    @TableField("tag")
    private String tag;
    /**
     * 其他相关字段
     */
    @TableField("other_field")
    private String otherField;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 是否删除标识，1为删除，0为未删除
     */
    @TableField("del_flag")
    private Integer delFlag;
    /**
     * 关联的部门
     */
    @TableField("dept_id")
    private Integer deptId;
    /**
     * 排序字段
     */
    @TableField("sort")
    private Integer sort;
    /**
     * 是否置顶，1为置顶，0为非置顶
     */
    @TableField("top_flag")
    private Integer topFlag;


    public static final String ID = "id";

    public static final String CODE = "code";

    public static final String NAME = "name";

    public static final String IMG = "img";

    public static final String SIMPLE_DESCRIPTION = "simple_description";

    public static final String DESCRIPTION = "description";

    public static final String TAG = "tag";

    public static final String OTHER_FIELD = "other_field";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String DEL_FLAG = "del_flag";

    public static final String DEPT_ID = "dept_id";

    public static final String SORT = "sort";

    public static final String TOP_FLAG = "top_flag";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public int compareTo(Advertising o) {
        return o.getSort() - this.sort;
    }
}
