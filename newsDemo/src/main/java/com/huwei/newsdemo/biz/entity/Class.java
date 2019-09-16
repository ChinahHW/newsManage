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
 * @since 2019-09-03
 */
@Data
@Accessors(chain = true)
public class Class extends Model<Class> implements Comparable<Class>{

    private static final long serialVersionUID = 1L;

    /**
     * 新闻分类ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("name")
    private String name;
    /**
     * 父类Id
     */
    @TableField("parent_id")
    private Integer parentId;
    /**
     * 删除标识,0未删除，1删除
     */
    @TableField("del_flag")
    private Integer delFlag;
    /**
     * 排序字段
     */
    @TableField("order")
    private Integer order;
    /**
     * 是否开放,0未开放，1开放
     */
    @TableField("open_flag")
    private Integer openFlag;
    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "create_user_id", fill = FieldFill.INSERT)
    private Integer createUserId;
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;
    @TableField(value = "update_user_id", fill = FieldFill.UPDATE)
    private Integer updateUserId;


    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String PARENT_ID = "parent_id";

    public static final String DEL_FLAG = "del_flag";

    public static final String ORDER = "order";

    public static final String OPEN_FLAG = "open_flag";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_USER_ID = "create_user_id";

    public static final String UPDATE_TIME = "update_time";

    public static final String UPDATE_USER_ID = "update_user_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


    @Override
    public int compareTo(Class o) {
        return o.getOrder() - this.order;
    }
}
