package com.huwei.newsdemo.biz.service;

import com.baomidou.mybatisplus.service.IService;
import com.huwei.newsdemo.biz.entity.Class;
import com.huwei.newsdemo.biz.entity.NewsClass;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huwei
 * @since 2019-09-04
 */
public interface INewsClassService extends IService<NewsClass> {

    List<Class> queryClassByNewsId(int newsId);
}
