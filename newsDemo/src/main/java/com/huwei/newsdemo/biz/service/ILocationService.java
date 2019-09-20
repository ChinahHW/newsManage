package com.huwei.newsdemo.biz.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
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
public interface ILocationService extends IService<Location> {

    Page<Location> queryByName(String locationKeyWord, int page, int count);

    List<Location> queryAll();

    Page<Location> queryPage(int page, int count);

    boolean delete(int locationId);

    String add(Location location);

}
