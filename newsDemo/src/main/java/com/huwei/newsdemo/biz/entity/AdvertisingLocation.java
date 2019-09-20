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
 * @since 2019-09-17
 */
@Data
@Accessors(chain = true)
@TableName("advertising_location")
public class AdvertisingLocation extends Model<AdvertisingLocation> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("advertising_id")
    private Integer advertisingId;
    @TableField("location_id")
    private Integer locationId;


    public static final String ID = "id";

    public static final String ADVERTISING_ID = "advertising_id";

    public static final String LOCATION_ID = "location_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
