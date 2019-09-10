package com.huwei.newsdemo.biz.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.huwei.newsdemo.biz.entity.News;
import com.huwei.newsdemo.biz.service.INewsService;
import com.huwei.newsdemo.response.BaseResponse;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huwei
 * @since 2019-08-26
 */

@CrossOrigin
@RestController
@RequestMapping("/biz/news")
public class NewsController extends BaseController{

    @Autowired
    private INewsService newsService;


    @RequestMapping("/add")
    public BaseResponse addNews(News news,int[] classId) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            String result = newsService.addNews(news,classId);
            if("add success".equals(result)){
                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/queryAll")
    public BaseResponse queryAll(){
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<News> newsList = newsService.queryAll();
            if(newsList != null){
                baseResponse.success(newsList);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("queryByKeyWord")
    public BaseResponse queryByName(String newsKeyWord){
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<News> newsList = newsService.queryByName(newsKeyWord);
            if(newsList != null){
                baseResponse.success(newsList);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/page")
    public BaseResponse getPage(int page, int count){
        BaseResponse baseResponse = new BaseResponse();
        try {
            Page<News> newsPage = newsService.queryByPage(page,count);
            if(newsPage != null){
                baseResponse.success(newsPage);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }




    @RequestMapping("/uploadImage")
    public String ckeditorUpload(@RequestParam("upload") MultipartFile file, String CKEditorFuncNum){
        String result = "";
        try {
            result = newsService.ckeditorUpload(file,CKEditorFuncNum);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return result;
    }

    @RequestMapping("/update")
    public BaseResponse update(News news,int[] classId){
        BaseResponse baseResponse = new BaseResponse();
        try {
            if(newsService.update(news,classId)){
                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/delete")
    public BaseResponse delete(int newsId){
        BaseResponse baseResponse = new BaseResponse();
        try {
            if(newsService.delete(newsId)){
                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

}

