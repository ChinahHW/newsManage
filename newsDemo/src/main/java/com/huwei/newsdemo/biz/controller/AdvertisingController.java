package com.huwei.newsdemo.biz.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.huwei.newsdemo.biz.entity.Advertising;
import com.huwei.newsdemo.biz.service.IAdvertisingService;
import com.huwei.newsdemo.response.BaseResponse;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @since 2019-09-17
 */
@RestController
@RequestMapping("/biz/advertising")
public class AdvertisingController extends BaseController{

    @Autowired
    private IAdvertisingService advertisingService;

    @RequestMapping("/add")
    public BaseResponse addAdvertising(Advertising advertising,int locationId) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            String result = advertisingService.addAdvertising(advertising,locationId    );
            if("添加成功".equals(result)){
                baseResponse.success();
            }
            baseResponse.setMsg(result);
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
            List<Advertising> advertisingList = advertisingService.queryAllByUserId(userId);
            if(advertisingList != null){
                baseResponse.success(advertisingList);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/queryByUserIdAndDeptId")
    public BaseResponse queryByUserIdAndDeptId(int page, int count, int userId,int deptId){
        BaseResponse baseResponse = new BaseResponse();
        try {
            Page<Advertising> advertisingList = advertisingService.queryByUserIdAndDeptId(page,count,userId,deptId);
            if(advertisingList != null){
                baseResponse.success(advertisingList);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("queryByKeyWord")
    public BaseResponse queryByName(String advertisingKeyWord, int userId,int page,int count){
        BaseResponse baseResponse = new BaseResponse();
        try {
            Page<Advertising> advertisingList = advertisingService.queryByName(advertisingKeyWord, userId, page, count);
            if(advertisingList != null){
                baseResponse.success(advertisingList);
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
            Page<Advertising> advertisingPage = advertisingService.queryPageByUserId(page,count,userId);
            if(advertisingPage != null){
                baseResponse.success(advertisingPage);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/update")
    public BaseResponse update(Advertising advertising){
        BaseResponse baseResponse = new BaseResponse();
        try {
            if(advertisingService.update(advertising)){
                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/delete")
    public BaseResponse delete(int advertisingId){
        BaseResponse baseResponse = new BaseResponse();
        try {
            if(advertisingService.delete(advertisingId)){
                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/uploadImg")
    public BaseResponse uploadImg(@RequestParam("file") MultipartFile file){
        BaseResponse baseResponse = new BaseResponse();
        try {
            String result = advertisingService.upload(file);
            baseResponse.success(result);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }
}

