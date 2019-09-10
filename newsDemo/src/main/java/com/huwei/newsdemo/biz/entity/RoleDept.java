package com.huwei.newsdemo.biz.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@TableName("role_dept")
public class RoleDept extends Model<RoleDept> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("role_id")
    private Integer roleId;
    @TableField("dept_id")
    private Integer deptId;


    public static final String ID = "id";

    public static final String ROLE_ID = "role_id";

    public static final String DEPT_ID = "dept_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
