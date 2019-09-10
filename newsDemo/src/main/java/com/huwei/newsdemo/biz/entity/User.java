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
 * @since 2019-09-09
 */
@Data
@Accessors(chain = true)
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "userId", type = IdType.AUTO)
    private Integer userId;
    @TableField("user_name")
    private String userName;
    @TableField("password")
    private String password;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 备注
     */
    @TableField("note")
    private String note;
    /**
     * 删除的标识，1表示删除，0表示未删除
     */
    @TableField("del_flag")
    private Integer delFlag;


    public static final String USERID = "userId";

    public static final String USER_NAME = "user_name";

    public static final String PASSWORD = "password";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String NOTE = "note";

    public static final String DEL_FLAG = "del_flag";

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

}
