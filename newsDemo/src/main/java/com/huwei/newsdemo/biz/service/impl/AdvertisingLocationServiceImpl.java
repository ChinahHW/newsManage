package com.huwei.newsdemo.biz.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.huwei.newsdemo.biz.dao.AdvertisingLocationDao;
import com.huwei.newsdemo.biz.entity.Advertising;
import com.huwei.newsdemo.biz.entity.AdvertisingLocation;
import com.huwei.newsdemo.biz.entity.Location;
import com.huwei.newsdemo.biz.service.IAdvertisingLocationService;
import com.huwei.newsdemo.biz.service.IAdvertisingService;
import com.huwei.newsdemo.biz.service.ILocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huwei
 * @since 2019-09-17
 */
@Service
public class AdvertisingLocationServiceImpl extends ServiceImpl<AdvertisingLocationDao, AdvertisingLocation> implements IAdvertisingLocationService {

    @Autowired
    private IAdvertisingService advertisingService;

    @Autowired
    private ILocationService locationService;

    @Override
    public List<Advertising> queryAdvertisingByLocationId(int locationId) {
        EntityWrapper<AdvertisingLocation> advertisingLocationEntityWrapper = new EntityWrapper<>();
        advertisingLocationEntityWrapper.where("location_id = {0}",locationId);
        List<AdvertisingLocation> advertisingLocationList = this.selectList(advertisingLocationEntityWrapper);
        List<Advertising> advertisingList = new ArrayList<>();
        if (advertisingLocationList != null) {
            for (AdvertisingLocation advertisingLocation : advertisingLocationList) {
                int advertisingId = advertisingLocation.getAdvertisingId();
                Advertising advertising = advertisingService.selectById(advertisingId);
                if(!advertisingList.contains(advertising)){
                    advertisingList.add(advertising);
                }
            }
        }
        return advertisingList;
    }

    @Override
    public List<Location> queryLocationByAdvertisingId(int advertisingId) {
        EntityWrapper<AdvertisingLocation> advertisingLocationEntityWrapper = new EntityWrapper<>();
        advertisingLocationEntityWrapper.where("advertising_id = {0}",advertisingId);
        List<AdvertisingLocation> advertisingLocationList = this.selectList(advertisingLocationEntityWrapper);
        List<Location> locationList = new ArrayList<>();
        if (advertisingLocationList != null) {
            for (AdvertisingLocation advertisingLocation : advertisingLocationList) {
                int locationId = advertisingLocation.getLocationId();
                Location location = locationService.selectById(locationId);
                if(!locationList.contains(location)){
                    locationList.add(location);
                }
            }
        }
        return locationList;
    }
}
