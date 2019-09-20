package com.huwei.newsdemo.biz.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.huwei.newsdemo.biz.entity.User;
import com.huwei.newsdemo.biz.service.IUserService;
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
@RequestMapping("/biz/user")
public class UserController extends BaseController{


    @Autowired
    private IUserService userService;

    @RequestMapping("/queryAll")
    public BaseResponse queryAll(int userId){
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<User> userList = userService.queryAll(userId);
            if(userList != null){
                baseResponse.success(userList);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("queryByKeyWord")
    public BaseResponse queryByName(String userKeyWord, int page, int count,int userId){
        BaseResponse baseResponse = new BaseResponse();
        try {
            Page<User> userPage = userService.queryByKeyWord(userKeyWord,page,count,userId);
            if(userPage != null){
                baseResponse.success(userPage);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }

        return baseResponse;
    }

    @RequestMapping("/page")
    public BaseResponse getPage(int page, int count, int userId){
        BaseResponse baseResponse = new BaseResponse();
        try {
            Page<User> userPage = userService.queryByPage(page,count,userId);
            if(userPage != null){
                baseResponse.success(userPage);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/login")
    public BaseResponse login(String userName,String password){
        BaseResponse baseResponse = new BaseResponse();
        try {
            User user = userService.toLogin(userName,password);
            if(user != null){
                baseResponse.success(user);
            }else{
                baseResponse.setMsg("账号或密码输入错误！！");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/add")
    public BaseResponse add(User user,int[] roles){
        BaseResponse baseResponse = new BaseResponse();
        try {
            if(userService.add(user,roles)){
                baseResponse.success();
            }else{
                baseResponse.setMsg("用户已存在，请勿重复添加！");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/update")
    public BaseResponse update(User user,int[] roles){
        BaseResponse baseResponse = new BaseResponse();
        try {
            if(userService.update(user,roles)){
                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/delete")
    public BaseResponse delete(User user){
        BaseResponse baseResponse = new BaseResponse();
        try {
            if(userService.delete(user)){
                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

}

