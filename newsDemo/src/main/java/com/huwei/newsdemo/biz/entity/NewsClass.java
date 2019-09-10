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
 * @since 2019-09-04
 */
@Data
@Accessors(chain = true)
@TableName("news_class")
public class NewsClass extends Model<NewsClass> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("news_id")
    private Integer newsId;
    @TableField("news_class_id")
    private Integer newsClassId;


    public static final String ID = "id";

    public static final String NEWS_ID = "news_id";

    public static final String NEWS_CLASS_ID = "news_class_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
