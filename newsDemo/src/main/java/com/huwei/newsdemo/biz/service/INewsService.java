package com.huwei.newsdemo.biz.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.huwei.newsdemo.biz.entity.News;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huwei
 * @since 2019-08-26
 */
public interface INewsService extends IService<News> {
    String addNews(News news,int[] classId);
    Page<News> queryByName(String newsKeyWord, int userId, int page, int count);
    List<News> queryAllByUserId(int userId);

    Page<News> queryByPage(int page, int count, int userId);


    /**
     * 富文本编辑器图片上传
     * @param file
     * @return
     */
    String ckeditorUpload(@RequestParam("upload") MultipartFile file, String CKEditorFuncNum) throws Exception;

    boolean update(News news,int[] classId);

    boolean delete(int newsId);

}
