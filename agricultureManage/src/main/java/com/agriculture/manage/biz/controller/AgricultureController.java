package com.agriculture.manage.biz.controller;


import com.agriculture.manage.biz.entity.Agriculture;
import com.agriculture.manage.biz.response.BaseResponse;
import com.agriculture.manage.biz.service.IAgricultureService;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huwei
 * @since 2019-09-14
 */
@RestController
@RequestMapping("/biz/agriculture")
public class AgricultureController extends BaseController{

    @Autowired
    private IAgricultureService agricultureService;

    @RequestMapping("/queryAll")
    public BaseResponse queryAll() {
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<Agriculture> agricultureList = agricultureService.selectList(null);
            baseResponse.success(agricultureList);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/upload")
    public String upload(@RequestParam("pdfFileAddress") MultipartFile file,
                         HttpServletRequest request, HttpServletResponse response) {
        System.out.println("come in");
        return JSON.toJSONString(agricultureService.upload(file,request,response));
    }

    @RequestMapping("/queryByPage")
    public BaseResponse upload(int page, int count) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            Page<Agriculture> agriculturePage = agricultureService.queryByPage(page,count);
            baseResponse.success(agriculturePage);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/update")
    public BaseResponse upload(Agriculture agriculture) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            boolean result = agricultureService.updateById(agriculture);
            baseResponse.success(result);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/delete")
    public BaseResponse delete(Agriculture agriculture) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            boolean result = agricultureService.deleteById(agriculture.getAgroId());
            baseResponse.success(result);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/insert")
    public BaseResponse insert(Agriculture agriculture) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            boolean result = agricultureService.insert(agriculture);
            baseResponse.success(result);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/printExcel")
    public BaseResponse printExcel() {
        BaseResponse baseResponse = new BaseResponse();
        try {
            boolean result2 = agricultureService.printExcel();
            baseResponse.success(result2);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/download")
    public BaseResponse download(HttpServletResponse response) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            agricultureService.download("16dbbae6-6a6d-433c-925a-74e80a2f163c.xls",response);
            baseResponse.success();
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

}

