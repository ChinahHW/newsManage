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
 * @since 2019-09-06
 */
@Data
@Accessors(chain = true)
public class Role extends Model<Role> implements Comparable<Role>{

    private static final long serialVersionUID = 1L;

    @TableId(value = "role_id", type = IdType.AUTO)
    private Integer roleId;
    @TableField("role_name")
    private String roleName;
    @TableField("role_code")
    private Integer roleCode;
    @TableField("role_desc")
    private String roleDesc;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 0--正常，1--删除
     */
    @TableField("del_flag")
    private Integer delFlag;


    public static final String ROLE_ID = "role_id";

    public static final String ROLE_NAME = "role_name";

    public static final String ROLE_CODE = "role_code";

    public static final String ROLE_DESC = "role_desc";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELFLAG = "del_flag";

    @Override
    protected Serializable pkVal() {
        return this.roleId;
    }

    @Override
    public int compareTo(Role o) {
        return o.getRoleId() - this.getRoleId();
    }
}
