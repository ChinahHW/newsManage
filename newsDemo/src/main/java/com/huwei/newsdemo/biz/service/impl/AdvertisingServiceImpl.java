package com.huwei.newsdemo.biz.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.huwei.newsdemo.biz.dao.AdvertisingDao;
import com.huwei.newsdemo.biz.entity.*;
import com.huwei.newsdemo.biz.service.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huwei
 * @since 2019-09-17
 */
@Service
public class AdvertisingServiceImpl extends ServiceImpl<AdvertisingDao, Advertising> implements IAdvertisingService {

    @Autowired
    private IAdvertisingLocationService advertisingLocationService;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IRoleDeptService roleDeptService;

    @Autowired
    private IDeptService deptService;

    // 注意路径格式，一般为项目路径下的一个文件夹里边，项目发布到linux服务器上又得改了
    private String imageFilePath = this.getClass().getClassLoader().getResource(".").getPath().replace("/target/classes/","/uploadImage/");



    @Override
    public String addAdvertising(Advertising advertising,int locationId) {
        EntityWrapper<Advertising> wrapper = new EntityWrapper<>();
        wrapper.where("code = {0}",advertising.getCode()).and().where("del_flag != {0}",1);
        List<Advertising> advertisingList = this.selectList(wrapper);
        if (advertisingList.size() != 0) {
            //说明已存在记录
            return "已存在该记录，请勿重复添加！！";
        }
        advertising.insert();
        AdvertisingLocation advertisingLocation = new AdvertisingLocation();
        advertisingLocation.setAdvertisingId(advertising.getId());
        advertisingLocation.setLocationId(locationId);
        advertisingLocation.insert();
        return "添加成功";
    }

    @Override
    public Page<Advertising> queryByName(String advertisingKeyWord, int userId, int page, int count) {
        Page<Advertising> advertisingPage = new Page<>();

        List<Advertising> advertisingList = new ArrayList<>();

        List<Dept> deptList = new ArrayList<>();
        //通过用户id查询角色，通过角色获得部门id
        EntityWrapper<UserRole> userRoleEntityWrapper = new EntityWrapper<>();
        userRoleEntityWrapper.where("user_id = {0}",userId);
        List<UserRole> userRoleList = userRoleService.selectList(userRoleEntityWrapper);
        if(userRoleList !=null & userRoleList.size() != 0){
            for (UserRole userRole : userRoleList) {
                EntityWrapper<RoleDept> roleDeptEntityWrapper = new EntityWrapper<>();
                roleDeptEntityWrapper.where("role_id = {0}",userRole.getRoleId());
                List<RoleDept> roleDeptList = roleDeptService.selectList(roleDeptEntityWrapper);
                if (roleDeptList != null & roleDeptList.size() != 0) {
                    for (RoleDept roleDept : roleDeptList) {
                        EntityWrapper<Dept> deptEntityWrapper = new EntityWrapper<>();
                        deptEntityWrapper.where("dept_id = {0}",roleDept.getDeptId());
                        List<Dept> deptList2 = deptService.selectList(deptEntityWrapper);
                        if (deptList2 != null & deptList2.size() != 0) {
                            for (Dept dept : deptList2) {
                                if(!deptList.contains(dept)){
                                    deptList.add(dept);
                                }
                                deptList = deptService.querySonDept(dept,deptList);
                                for (Dept dept1 : deptList) {
                                    EntityWrapper<Advertising> wrapper = new EntityWrapper<>();
                                    wrapper.where("dept_id = {0}",dept1     .getDeptId()).and().where("del_flag != {0}",1).orderBy("advertising.sort");
                                    if(advertisingKeyWord != null){
                                        wrapper.like("code",advertisingKeyWord).or().like("name",advertisingKeyWord).or().like("description",advertisingKeyWord).or().like("tag",advertisingKeyWord);
                                    }
                                    List<Advertising> advertisingList1 = this.selectList(wrapper);
                                    if (advertisingList1 != null & advertisingList1.size() != 0) {
                                        for (Advertising advertising : advertisingList1) {
                                            if(!advertisingList.contains(advertising)){
                                                advertisingList.add(advertising);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Collections.sort(advertisingList);
        List<Advertising> advertisingOnePageList = new ArrayList<>();
        if (page * count > advertisingList.size()) {
            advertisingOnePageList = advertisingList.subList((page - 1) * count,advertisingList.size());
        }else{
            advertisingOnePageList = advertisingList.subList((page - 1) * count,(page - 1) * count + count);
        }

        advertisingPage.setRecords(advertisingOnePageList);
        advertisingPage.setTotal(advertisingList.size());
        return advertisingPage;
    }

    @Override
    public List<Advertising> queryAllByUserId(int userId) {
        EntityWrapper<Advertising> wrapper = new EntityWrapper<>();
        wrapper.where("dept_id = {0}",userId).and().where("del_flag != {0}",1).orderBy("advertising.sort");;
        List<Advertising> advertisingList = this.selectList(wrapper);
        return advertisingList;
    }

    @Override
    public Page<Advertising> queryPageByUserId(int page, int count, int userId) {
        Page<Advertising> advertisingPage = queryByName(null,userId,page,count);
        return advertisingPage;
    }

    @Override
    public boolean update(Advertising advertising) {
        return advertising.updateById();
    }

    @Override
    public boolean delete(int advertisingId) {
        Advertising advertising = selectById(advertisingId);
        advertising.setDelFlag(1);
        //同步删除advertising-location表中的信息
        EntityWrapper<AdvertisingLocation> wrapper = new EntityWrapper<>();
        wrapper.where("advertising_id = {0}",advertisingId);
        List<AdvertisingLocation> advertisingLocationList = advertisingLocationService.selectList(wrapper);
        if (advertisingLocationList != null) {
            for (AdvertisingLocation advertisingLocation : advertisingLocationList) {
                advertisingLocation.deleteById();
            }
        }
        advertising.updateById();
        return true;
    }

    @Override
    public Page<Advertising> queryByUserIdAndDeptId(int page, int count, int userId, int deptId) {
        Page<Advertising> advertisingPage = new Page<>(page,count);

        if (deptId == 0) {
            advertisingPage = queryPageByUserId(page,count,userId);
        }else{
            Dept dept = deptService.selectById(deptId);
            //通过部门id查询出当前部门及其子部门，将这些部门下的广告添加
            List<Advertising> advertisingList = new ArrayList<>();
            List<Dept> deptList = new ArrayList<>();
            deptList.add(dept);
            deptList = deptService.querySonDept(dept,deptList);
            if (deptList != null) {
                for (Dept dept1 : deptList) {
                    EntityWrapper<Advertising> advertisingEntityWrapper = new EntityWrapper<>();
                    advertisingEntityWrapper.where("del_flag != {0}",1);
                    advertisingEntityWrapper.where("dept_id = {0}",dept1.getDeptId()).orderBy("advertising.sort");;
                    List<Advertising> advertisingList1 = selectList(advertisingEntityWrapper);
                    if(advertisingList1 != null) {
                        for (Advertising advertising : advertisingList1) {
                            if(!advertisingList.contains(advertising)){
                                advertisingList.add(advertising);
                            }
                        }
                    }
                }
            }
            Collections.sort(advertisingList);
            List<Advertising> advertisingOnePageList = new ArrayList<>();
            if (page * count > advertisingList.size()) {
                advertisingOnePageList = advertisingList.subList((page - 1) * count,advertisingList.size());
            }else{
                advertisingOnePageList = advertisingList.subList((page - 1) * count,(page - 1) * count + count);
            }

            advertisingPage.setRecords(advertisingOnePageList);
            advertisingPage.setTotal(advertisingList.size());
            return advertisingPage;

        }
        return advertisingPage;
    }

    public String upload(@RequestParam("file") MultipartFile file) throws Exception {

        String classPath = "";
        List<String> str = new ArrayList<>();

        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //实际处理肯定是要加上一段唯一的字符串（如现在时间），这里简单加 cun
        String newFileName = UUID.randomUUID() + suffixName;
        //使用架包 common-io实现图片上传
        FileUtils.copyInputStreamToFile(file.getInputStream(), new File(imageFilePath + newFileName));
        JSONObject result = new JSONObject();
        return newFileName;
    }
}
