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
 * @since 2019-09-12
 */
@Data
@Accessors(chain = true)
public class News extends Model<News>  implements Comparable<News>{

    private static final long serialVersionUID = 1L;

    @TableId(value = "news_id", type = IdType.AUTO)
    private Integer newsId;
    @TableField("news_title")
    private String newsTitle;
    @TableField("news_tag")
    private String newsTag;
    @TableField("news_content")
    private String newsContent;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 0为未删除，1为已删除
     */
    @TableField("del_flag")
    private Integer delFlag;
    @TableField("sort")
    private Integer sort;


    public static final String NEWSID = "newsId";

    public static final String NEWS_TITLE = "news_title";

    public static final String NEWS_TAG = "news_tag";

    public static final String NEWS_CONTENT = "news_content";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String DEL_FLAG = "del_flag";

    public static final String SORT = "sort";

    @Override
    protected Serializable pkVal() {
        return this.newsId;
    }

    @Override
    public int compareTo(News o) {
        return o.getSort() - this.sort;
    }
}
