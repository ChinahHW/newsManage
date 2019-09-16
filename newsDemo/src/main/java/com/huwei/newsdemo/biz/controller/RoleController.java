package com.huwei.newsdemo.biz.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.huwei.newsdemo.biz.entity.Role;
import com.huwei.newsdemo.biz.service.IRoleService;
import com.huwei.newsdemo.response.BaseResponse;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huwei
 * @since 2019-09-06
 */
@RestController
@RequestMapping("/biz/role")
public class RoleController extends BaseController{

    @Autowired
    private IRoleService roleService;

    @RequestMapping("/add")
    public BaseResponse add(Role role,int[] permission, int[] depts){
        BaseResponse baseResponse = new BaseResponse();
        try {
            String result = roleService.add(role,permission,depts);
            if(result.equals("success")){
                baseResponse.success();
            }else{
                baseResponse.setMsg(result);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("queryByKeyWord")
    public BaseResponse queryByName(String roleKeyWord, int page, int count){
        BaseResponse baseResponse = new BaseResponse();
        try {
            Page<Role> rolePage = roleService.queryByKeyWord(roleKeyWord,page,count);
            if(rolePage != null){
                baseResponse.success(rolePage);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }

        return baseResponse;
    }

    @RequestMapping("/page")
    public BaseResponse page(int page,int count){
        BaseResponse baseResponse = new BaseResponse();
        try {
            baseResponse.success(roleService.queryByPage(page,count));
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/update")
    public BaseResponse update(Role role,int[] permission,int[] depts){
        BaseResponse baseResponse = new BaseResponse();
        try {
            baseResponse.success(roleService.update(role,permission,depts));
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/delete")
    public BaseResponse delete(Role role){
        BaseResponse baseResponse = new BaseResponse();
        try {
            baseResponse.success(roleService.delete(role));
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
            baseResponse.success(roleService.queryAll());
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }
}

