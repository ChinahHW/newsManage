package com.huwei.newsdemo.biz.controller;


import com.huwei.newsdemo.biz.entity.Class;
import com.huwei.newsdemo.biz.service.IDeptClassService;
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
 * @since 2019-09-11
 */
@RestController
@RequestMapping("/biz/deptClass")
public class DeptClassController extends BaseController{

    @Autowired
    private IDeptClassService deptClassService;

    @RequestMapping("/queryClassByDeptId")
    public BaseResponse queryClassByNewsId(int deptId){
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<Class> classList = deptClassService.queryClassByDeptId(deptId);
            baseResponse.success(classList);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }
}

