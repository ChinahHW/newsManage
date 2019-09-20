package com.huwei.newsdemo.biz.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.huwei.newsdemo.biz.entity.Location;
import com.huwei.newsdemo.biz.dao.LocationDao;
import com.huwei.newsdemo.biz.service.ILocationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class LocationServiceImpl extends ServiceImpl<LocationDao, Location> implements ILocationService {

    @Override
    public Page<Location> queryByName(String locationKeyWord, int page, int count) {
        EntityWrapper<Location> locationEntityWrapper = new EntityWrapper<>();
        if (locationKeyWord != null) {
            locationEntityWrapper.where("del_flag != {0}",1).and().like("name",locationKeyWord).or().like("location",locationKeyWord)
                    .or().where("description",locationKeyWord);
        }
        Page<Location> locationPage = new Page<>(page,count);
        locationPage = selectPage(locationPage,locationEntityWrapper);
        return locationPage;
    }

    @Override
    public List<Location> queryAll() {
        EntityWrapper<Location> locationEntityWrapper = new EntityWrapper<>();
        locationEntityWrapper.where("del_flag != {0}",1);
        return selectList(locationEntityWrapper);
    }

    @Override
    public Page<Location> queryPage(int page, int count) {
        EntityWrapper<Location> locationEntityWrapper = new EntityWrapper<>();
        locationEntityWrapper.where("del_flag != {0}",1);
        Page<Location> locationPage = new Page<>(page,count);
        locationPage = selectPage(locationPage,locationEntityWrapper);
        return locationPage;
    }

    @Override
    public boolean delete(int locationId) {
        Location location = selectById(locationId);
        location.setDelFlag(1);
        return location.updateById();
    }

    @Override
    public String add(Location location) {
        EntityWrapper<Location> locationEntityWrapper = new EntityWrapper<>();
        locationEntityWrapper.where("del_flag != {0}",1).and().where("name = {0}",location.getName());
        List<Location> locationList = selectList(locationEntityWrapper);
        if(locationList != null & locationList.size() != 0){
            return "广告位置已经存在，清楚重复添加！！";
        }
        insert(location);
        return "添加成功";
    }
}
