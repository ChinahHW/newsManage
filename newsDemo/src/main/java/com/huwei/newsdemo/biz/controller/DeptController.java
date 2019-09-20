package com.huwei.newsdemo.biz.controller;


import com.huwei.newsdemo.biz.entity.Dept;
import com.huwei.newsdemo.biz.service.IDeptService;
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
@RequestMapping("/biz/dept")
public class DeptController extends BaseController{

    @Autowired
    private IDeptService deptService;

    @RequestMapping("/queryTreeDept")
    public BaseResponse queryTreeDept(int userId){
        BaseResponse baseResponse = new BaseResponse();
        try {
            baseResponse.success(deptService.queryTreeDept(userId));
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/queryTreeForList")
    public BaseResponse queryTreeForList(int userId){
        BaseResponse baseResponse = new BaseResponse();
        try {
            baseResponse.success(deptService.queryTreeForList(userId));
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/add")
    public BaseResponse add(Dept dept,int[] classId,int userId){
        BaseResponse baseResponse = new BaseResponse();
        try {
            boolean result = deptService.add(dept,classId,userId);
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
    public BaseResponse delete(Dept dept){
        BaseResponse baseResponse = new BaseResponse();
        try {
            boolean result = deptService.delNode(dept);
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
    public BaseResponse update(Dept dept,int[] classId){
        BaseResponse baseResponse = new BaseResponse();
        try {
            boolean result = deptService.update(dept,classId);
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
    public BaseResponse queryAll(int userId){
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<Dept> treeDepts = deptService.queryAll(userId);
            baseResponse.success(treeDepts);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/queryById")
    public BaseResponse queryById(Dept dept){
        BaseResponse baseResponse = new BaseResponse();
        try {
            Dept dept11 = deptService.queryById(dept);
            baseResponse.success(dept11);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

}

