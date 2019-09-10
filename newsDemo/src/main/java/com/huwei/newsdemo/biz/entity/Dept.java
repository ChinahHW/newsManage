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
public class Dept extends Model<Dept> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "dept_id", type = IdType.AUTO)
    private Integer deptId;
    @TableField("dept_name")
    private String deptName;
    @TableField("sort")
    private Integer sort;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableField("del_flag")
    private Integer delFlag;
    @TableField("parent_id")
    private Integer parentId;


    public static final String DEPT_ID = "dept_id";

    public static final String DEPT_NAME = "dept_name";

    public static final String SORT = "sort";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String DEL_FLAG = "del_flag";

    public static final String PARENT_ID = "parent_id";

    @Override
    protected Serializable pkVal() {
        return this.deptId;
    }

}
