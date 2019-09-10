package com.huwei.newsdemo.biz.controller;


import com.huwei.newsdemo.biz.entity.Dept;
import com.huwei.newsdemo.biz.service.IRoleDeptService;
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
@RequestMapping("/biz/roleDept")
public class RoleDeptController extends BaseController{
    @Autowired
    private IRoleDeptService roleDeptService;

    @RequestMapping("/queryDeptByRoleId")
    public BaseResponse queryMenuByRoleId(int roleId){
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<Dept> depts = roleDeptService.queryDeptByRoleId(roleId);
            baseResponse.success(depts);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }
}

