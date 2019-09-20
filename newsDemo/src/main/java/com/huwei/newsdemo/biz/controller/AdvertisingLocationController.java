package com.huwei.newsdemo.biz.controller;


import com.huwei.newsdemo.biz.entity.Advertising;
import com.huwei.newsdemo.biz.entity.Location;
import com.huwei.newsdemo.biz.service.IAdvertisingLocationService;
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
@RequestMapping("/biz/advertisingLocation")
public class AdvertisingLocationController extends BaseController{

    @Autowired
    private IAdvertisingLocationService advertisingLocationService;

    @RequestMapping("/queryAdvertisingByLocationId")
    public BaseResponse queryAdvertisingByLocationId(int locationId){
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<Advertising> advertisingList = advertisingLocationService.queryAdvertisingByLocationId(locationId);
            if (advertisingList != null) {
                baseResponse.success(advertisingList);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping("/queryLocationByAdvertisingId")
    public BaseResponse queryLocationByAdvertisingId(int advertisingId){
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<Location> locationList = advertisingLocationService.queryLocationByAdvertisingId(advertisingId);
            if (locationList != null) {
                baseResponse.success(locationList);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

}

