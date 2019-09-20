package com.huwei.newsdemo.biz.service;

import com.baomidou.mybatisplus.service.IService;
import com.huwei.newsdemo.biz.entity.Advertising;
import com.huwei.newsdemo.biz.entity.AdvertisingLocation;
import com.huwei.newsdemo.biz.entity.Location;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huwei
 * @since 2019-09-17
 */
public interface IAdvertisingLocationService extends IService<AdvertisingLocation> {
    List<Advertising> queryAdvertisingByLocationId(int locationId);

    List<Location> queryLocationByAdvertisingId(int advertisingId);
}
