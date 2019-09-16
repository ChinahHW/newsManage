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
 * @since 2019-09-11
 */
@Data
@Accessors(chain = true)
@TableName("dept_class")
public class DeptClass extends Model<DeptClass> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("dept_id")
    private Integer deptId;
    @TableField("class_id")
    private Integer classId;


    public static final String ID = "id";

    public static final String DEPT_ID = "dept_id";

    public static final String CLASS_ID = "class_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
