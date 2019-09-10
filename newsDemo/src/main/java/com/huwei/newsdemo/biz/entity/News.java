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
 * @since 2019-08-26
 */
@Data
@Accessors(chain = true)
public class News extends Model<News> {

    private static final long serialVersionUID = 1L;

    /**
     * 新闻唯一标识
     */
    @TableId(value = "newsId", type = IdType.AUTO)
    private Integer newsId;
    /**
     * 新闻标题
     */
    @TableField("news_title")
    private String newsTitle;
    /**
     * 新闻标签
     */
    @TableField("news_tag")
    private String newsTag;
    /**
     * 新闻内容
     */
    @TableField("news_content")
    private String newsContent;
    /**
     * 创建时间
     */
    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private Date gmtCreate;
    /**
     * 修改人信息
     */
    @TableField(value = "gmt_modify", fill = FieldFill.INSERT_UPDATE)
    private String gmtModify;


    public static final String NEWSID = "newsId";

    public static final String NEWS_TITLE = "news_title";

    public static final String NEWS_TAG = "news_tag";

    public static final String NEWS_CONTENT = "news_content";

    public static final String GMT_CREATE = "gmt_create";

    public static final String GMT_MODIFY = "gmt_modify";

    @Override
    protected Serializable pkVal() {
        return this.newsId;
    }

}
