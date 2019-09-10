package com.huwei.newsdemo.biz.controller;


import com.huwei.newsdemo.biz.entity.Menu;
import com.huwei.newsdemo.biz.entity.User;
import com.huwei.newsdemo.biz.service.IMenuService;
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
 * @since 2019-09-06
 */
@RestController
@RequestMapping("/biz/menu")
public class MenuController extends BaseController{

    @Autowired
    private IMenuService menuService;

    @RequestMapping("/queryTreeMenu")
    public BaseResponse queryTreeMenu(User user){
        BaseResponse baseResponse = new BaseResponse();
        try {
            baseResponse.success(menuService.queryTreeMenu(user));
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/add")
    public BaseResponse add(Menu menu){
        BaseResponse baseResponse = new BaseResponse();
        try {
            boolean result = menuService.insert(menu);
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
    public BaseResponse delete(Menu menu){
        BaseResponse baseResponse = new BaseResponse();
        try {
            boolean result = menuService.delNode(menu);
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
    public BaseResponse update(Menu menu){
        BaseResponse baseResponse = new BaseResponse();
        try {
            boolean result = menuService.updateById(menu);
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
            List<Menu> treeMenus = menuService.queryAll();
            baseResponse.success(treeMenus);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/queryById")
    public BaseResponse queryById(Menu menu){
        BaseResponse baseResponse = new BaseResponse();
        try {
            Menu menu1 = menuService.queryById(menu);
            baseResponse.success(menu1);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

}

