package com.huwei.newsdemo.biz.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.huwei.newsdemo.biz.entity.Class;
import com.huwei.newsdemo.biz.entity.NewsClass;
import com.huwei.newsdemo.biz.dao.NewsClassDao;
import com.huwei.newsdemo.biz.service.INewsClassService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huwei
 * @since 2019-09-04
 */
@Service
public class NewsClassServiceImpl extends ServiceImpl<NewsClassDao, NewsClass> implements INewsClassService {

    @Override
    public List<Class> queryClassByNewsId(int newsId) {
        EntityWrapper<NewsClass> wrapper = new EntityWrapper<>();
        wrapper.where("news_id = {0}",newsId);
        List<NewsClass> newsClassList = this.selectList(wrapper);
        List<Class> classList = new ArrayList<>();
        Class clazz = new Class();
        if(newsClassList != null){
            for (NewsClass newsClass : newsClassList) {
                int classId = newsClass.getNewsClassId();
                clazz.setId(classId);
                classList.add(clazz.selectById());
            }
        }
        return classList;
    }
}
