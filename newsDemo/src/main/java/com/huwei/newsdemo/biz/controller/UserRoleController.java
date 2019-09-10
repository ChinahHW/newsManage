package com.huwei.newsdemo.biz.controller;


import com.huwei.newsdemo.biz.entity.Role;
import com.huwei.newsdemo.biz.service.IUserRoleService;
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
@RequestMapping("/biz/userRole")
public class UserRoleController extends BaseController{

    @Autowired
    private IUserRoleService userRoleService;

    @RequestMapping("/queryRoleByUserId")
    public BaseResponse queryRoleByUserId(int userId){
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<Role> roleList = userRoleService.queryRoleByUserId(userId);
            baseResponse.success(roleList);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }
}

