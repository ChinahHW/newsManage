package com.huwei.newsdemo.biz.controller;


import com.huwei.newsdemo.biz.entity.Class;
import com.huwei.newsdemo.biz.service.IClassService;
import com.huwei.newsdemo.response.BaseResponse;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huwei
 * @since 2019-09-03
 */
@RestController
@RequestMapping("/biz/class")
public class ClassController extends BaseController{

    @Autowired
    private IClassService classService;

    @RequestMapping("/queryTreeMenu")
    public BaseResponse queryTreeMenu(){
        BaseResponse baseResponse = new BaseResponse();
        try {
            String treeMenus = classService.queryTreeMenu();
            baseResponse.success(treeMenus);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/add")
    public BaseResponse add(Class newsClass){
        BaseResponse baseResponse = new BaseResponse();
        try {
            boolean result = classService.insert(newsClass);
            if(result){
                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/delete")
    public BaseResponse delete(Class newsClass){
        BaseResponse baseResponse = new BaseResponse();
        try {
            boolean result = classService.delNode(newsClass);
            if(result){
                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/update")
    public BaseResponse update(Class newsClass){
        BaseResponse baseResponse = new BaseResponse();
        try {
            newsClass.setUpdateUserId(1);
            boolean result = classService.updateById(newsClass);
            if(result){
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
            List<Class> treeMenus = classService.queryAll();
            baseResponse.success(treeMenus);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/queryById")
    public BaseResponse queryById(Class clazz){
        BaseResponse baseResponse = new BaseResponse();
        try {
            Class newsClass = classService.queryById(clazz);
            baseResponse.success(newsClass);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }
}

