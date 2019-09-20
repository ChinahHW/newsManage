package com.huwei.newsdemo.biz.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.huwei.newsdemo.biz.entity.Advertising;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huwei
 * @since 2019-09-17
 */
public interface IAdvertisingService extends IService<Advertising> {

    String addAdvertising(Advertising advertising,int locationId);

    Page<Advertising> queryByName(String advertisingKeyWord, int userId, int page, int count);

    List<Advertising> queryAllByUserId(int userId);

    Page<Advertising> queryPageByUserId(int page, int count, int userId);


    boolean update(Advertising advertising);

    boolean delete(int AdvertisingId);

    Page<Advertising> queryByUserIdAndDeptId(int page, int count, int userId,int deptId);

    String upload(@RequestParam("file") MultipartFile file) throws Exception;
}
