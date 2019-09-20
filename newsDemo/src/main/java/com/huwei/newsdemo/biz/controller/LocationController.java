package com.huwei.newsdemo.biz.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.huwei.newsdemo.biz.entity.Location;
import com.huwei.newsdemo.biz.service.ILocationService;
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
 * @since 2019-09-17
 */
@RestController
@RequestMapping("/biz/location")
public class LocationController extends BaseController{

    @Autowired
    private ILocationService locationService;

    @RequestMapping("/add")
    public BaseResponse add(Location location) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            String result = locationService.add(location);
            if ("添加成功".equals(result)) {
                baseResponse.success();
            }
            baseResponse.setMsg(result);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/update")
    public BaseResponse update(Location location) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            Boolean result = locationService.updateById(location);
            if (result) {
                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/delete")
    public BaseResponse update(int locationId) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            Boolean result = locationService.delete(locationId);
            if (result) {
                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/page")
    public BaseResponse queryPage(int page,int count) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            Page<Location> locationPage = locationService.queryPage(page,count);
            baseResponse.success(locationPage);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/queryAll")
    public BaseResponse queryAll() {
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<Location> locationList = locationService.queryAll();
            baseResponse.success(locationList);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/queryByKeyWord")
    public BaseResponse queryPage(String locationKeyWord, int page, int count) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            Page<Location> locationPage = locationService.queryByName(locationKeyWord,page,count);
            baseResponse.success(locationPage);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }
}

