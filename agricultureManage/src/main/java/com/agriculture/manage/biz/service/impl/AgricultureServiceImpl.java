package com.agriculture.manage.biz.service.impl;

import com.agriculture.manage.biz.entity.Agriculture;
import com.agriculture.manage.biz.dao.AgricultureDao;
import com.agriculture.manage.biz.service.IAgricultureService;
import com.agriculture.manage.util.ExcelUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huwei
 * @since 2019-09-14
 */
@Service
public class AgricultureServiceImpl extends ServiceImpl<AgricultureDao, Agriculture> implements IAgricultureService {

    int agro_typeLocation = 0; //作物类型
    int yearLocation = 1;   //实验年份
    int areaLocation = 2;   //区域
    int test_locationLocation = 3;  //试验地点
    int longitudeLocation = 4;  //经度
    int latitudeLocation = 5;   //纬度
    int altitudeLocation = 6;  //海拔高度（m）
    int average_annual_temperatureLocation = 7;  //年均温
    int sunshine_durationLocation = 8;  //日照时数h
    int active_accumulated_temperatureLocation = 9;  //≥10℃活动积温℃
    int annual_precipitationLocation = 10;  //年降水量
    int frost_free_seasonLocation = 11;  //无霜期
    int soil_textureLocation = 12;  //土壤质地
    int soil_bulk_densityLocation = 13;  //0-100cm土壤容重g/cm3
    int membrane_typeLocation = 14;  //地膜类型

    int membrane_colorLocation = 15;    //地膜颜色

    int mulch_methodLocation = 16;  //地膜覆盖方式
    int number_of_replicationLocation = 17;  //重复数
    int mulch_yieldLocation =18;  //地膜覆盖产量
    int bare_land_productionLocation = 19;  //裸地产量

    int mulch_yield_wueLocation = 20;   //地膜覆盖WUE(kg/m3)

    int mulch_wueLocation = 21;  //裸地WUE(kg/m3)
    int average_daily_temperatureLocation = 22;  //生育期土壤5-25cm日均增温℃
    int article_nameLocation = 23;  //文章名称
    int authorLocation = 24;  //作者


    @Override
    public List<Agriculture> queryAll() {
        return null;
    }

    @Override
    public Page<Agriculture> queryByKeyWord(String keyWord) {
        return null;
    }

    @Override
    public String add(Agriculture agriculture, int[] permission, int[] depts) {
        return null;
    }

    @Override
    public boolean update(Agriculture agriculture, int[] permission, int[] depts) {
        return false;
    }

    @Override
    public Page<Agriculture> queryByPage(int page, int count) {
        Page<Agriculture> agriculturePage = new Page<>(page,count);
        agriculturePage = this.selectPage(agriculturePage,null);
        return agriculturePage;
    }

    @Override
    public boolean delete(Agriculture agriculture) {
        return false;
    }

    @Override
    public String upload(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        if(!file.isEmpty()){
            String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/";
            File dir = new File(filePath);
            if(! dir.exists()) {
                dir.mkdir();
            }
            String path = filePath + file.getOriginalFilename();
            File tempFile = null;
            try {
                tempFile = new File(path);
                file.transferTo(tempFile);
                List<List<String>> excel = ExcelUtil.readerExcel(tempFile);
                List<Agriculture> agricultureList = new ArrayList<>();

                int size = excel.size();

                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < excel.get(i).size(); j++) {
                        if(excel.get(i).get(j).equals("作物类型")){
                            agro_typeLocation = j;
                        }else if(excel.get(i).get(j).equals("实验年份")){
                            yearLocation = j;
                        }else if(excel.get(i).get(j).equals("区域")){
                            areaLocation = j;
                        }else if(excel.get(i).get(j).equals("试验地点")){
                            test_locationLocation = j;
                        }else if(excel.get(i).get(j).equals("经度")){
                            longitudeLocation = j;
                        }else if(excel.get(i).get(j).equals("纬度")){
                            latitudeLocation = j;
                        }else if(excel.get(i).get(j).equals("海拔高度（m）")){
                            altitudeLocation = j;
                        }else if(excel.get(i).get(j).equals("年均温")){
                            average_annual_temperatureLocation = j;
                        }else if(excel.get(i).get(j).equals("日照时数h")){
                            sunshine_durationLocation = j;
                        }else if(excel.get(i).get(j).equals("≥10℃活动积温℃")){
                            active_accumulated_temperatureLocation = j;
                        }else if(excel.get(i).get(j).equals("年降水量")){
                            annual_precipitationLocation = j;
                        }else if(excel.get(i).get(j).equals("无霜期")){
                            frost_free_seasonLocation = j;
                        }else if(excel.get(i).get(j).equals("土壤质地")){
                            soil_textureLocation = j;
                        }else if(excel.get(i).get(j).equals("0-100cm土壤容重g/cm3")){
                            soil_bulk_densityLocation = j;
                        }else if(excel.get(i).get(j).equals("地膜类型")){
                            membrane_typeLocation = j;
                        }else if(excel.get(i).get(j).equals("地膜覆盖方式")){
                            mulch_methodLocation = j;
                        }else if(excel.get(i).get(j).equals("重复数")){
                            number_of_replicationLocation = j;
                        }else if(excel.get(i).get(j).equals("地膜覆盖产量")){
                            mulch_yieldLocation = j;
                        }else if(excel.get(i).get(j).equals("裸地产量")){
                            bare_land_productionLocation = j;
                        }else if(excel.get(i).get(j).equals("裸地WUE(kg/m3)")){
                            mulch_wueLocation = j;
                        }else if(excel.get(i).get(j).equals("生育期土壤5-25cm日均增温℃")){
                            average_daily_temperatureLocation = j;
                        }else if(excel.get(i).get(j).equals("文章名称")){
                            article_nameLocation = j;
                        }else if(excel.get(i).get(j).equals("作者")){
                            authorLocation = j;
                        }
                        if(!excel.get(i).get(0).equals("作物类型") && excel.get(i).get(0) != null){
                            Agriculture agriculture = new Agriculture();
                            agriculture.setAgroType(excel.get(i).get(agro_typeLocation));
                            if(excel.get(i).get(active_accumulated_temperatureLocation) != null && !excel.get(i).get(active_accumulated_temperatureLocation).equals("无")){
                                agriculture.setActiveAccumulatedTemperature(Double.parseDouble(excel.get(i).get(active_accumulated_temperatureLocation)));
                            }else{
                                agriculture.setActiveAccumulatedTemperature(0.0);
                            }
                            if(excel.get(i).get(yearLocation) != null && !excel.get(i).get(yearLocation).equals("无")){
                                agriculture.setYear(Integer.parseInt(excel.get(i).get(yearLocation)));
                            }else{
                                agriculture.setYear(null);
                            }
                            agriculture.setArea(excel.get(i).get(areaLocation));
                            agriculture.setTestLocation(excel.get(i).get(test_locationLocation));
                            if(excel.get(i).get(longitudeLocation) != null && !excel.get(i).get(longitudeLocation).equals("无")){
                                agriculture.setLongitude(Double.parseDouble(excel.get(i).get(longitudeLocation)));
                            }else{
                                agriculture.setLongitude(0.0);
                            }
                            if(excel.get(i).get(latitudeLocation) != null && !excel.get(i).get(latitudeLocation).equals("无")){
                                agriculture.setLatitude(Double.parseDouble(excel.get(i).get(latitudeLocation)));
                            }else{
                                agriculture.setLatitude(0.0);
                            }
                            if(excel.get(i).get(altitudeLocation) != null && !excel.get(i).get(altitudeLocation).equals("无")){
                                agriculture.setAltitude(Double.parseDouble(excel.get(i).get(altitudeLocation)));
                            }else{
                                agriculture.setAltitude(0.0);
                            }
                            if(excel.get(i).get(average_annual_temperatureLocation) != null && !excel.get(i).get(average_annual_temperatureLocation).equals("无")){
                                agriculture.setAverageAnnualTemperature(Double.parseDouble(excel.get(i).get(average_annual_temperatureLocation)));
                            }else{
                                agriculture.setAverageAnnualTemperature(0.0);
                            }
                            if(excel.get(i).get(sunshine_durationLocation) != null && !excel.get(i).get(sunshine_durationLocation).equals("无")){
                                agriculture.setSunshineDuration(Double.parseDouble(excel.get(i).get(sunshine_durationLocation)));
                            }else{
                                agriculture.setSunshineDuration(0.0);
                            }
                            if(excel.get(i).get(active_accumulated_temperatureLocation) != null && !excel.get(i).get(active_accumulated_temperatureLocation).equals("无")){
                                agriculture.setActiveAccumulatedTemperature(Double.parseDouble(excel.get(i).get(active_accumulated_temperatureLocation)));
                            }else{
                                agriculture.setActiveAccumulatedTemperature(0.0);
                            }
                            if(excel.get(i).get(annual_precipitationLocation) != null && !excel.get(i).get(annual_precipitationLocation).equals("无")){
                                agriculture.setAnnualPrecipitation(Double.parseDouble(excel.get(i).get(annual_precipitationLocation)));
                            }else{
                                agriculture.setAnnualPrecipitation(0.0);
                            }
                            if(excel.get(i).get(frost_free_seasonLocation) != null && !excel.get(i).get(frost_free_seasonLocation).equals("无")){
                                agriculture.setFrostFreeSeason(Double.parseDouble(excel.get(i).get(frost_free_seasonLocation)));
                            }else{
                                agriculture.setFrostFreeSeason(0.0);
                            }
                            agriculture.setSoilTexture(excel.get(i).get(soil_textureLocation));
                            if(excel.get(i).get(soil_bulk_densityLocation) != null && !excel.get(i).get(soil_bulk_densityLocation).equals("无")){
                                agriculture.setSoilBulkDensity(Double.parseDouble(excel.get(i).get(soil_bulk_densityLocation)));
                            }else{
                                agriculture.setSoilBulkDensity(0.0);
                            }
                            agriculture.setMembraneType(excel.get(i).get(membrane_typeLocation));
                            agriculture.setMulchMethod(excel.get(i).get(mulch_methodLocation));
                            if(excel.get(i).get(number_of_replicationLocation) != null && !excel.get(i).get(number_of_replicationLocation).equals("无")){
                                agriculture.setNumberOfReplication(Integer.parseInt(excel.get(i).get(number_of_replicationLocation)));
                            }else{
                                agriculture.setNumberOfReplication(0);
                            }
                            if(excel.get(i).get(mulch_yieldLocation) != null && !excel.get(i).get(mulch_yieldLocation).equals("无")){
                                agriculture.setMulchYield(Double.parseDouble(excel.get(i).get(mulch_yieldLocation)));
                            }else{
                                agriculture.setMulchYield(0.0);
                            }
                            if(excel.get(i).get(bare_land_productionLocation) != null && !excel.get(i).get(bare_land_productionLocation).equals("无")){
                                agriculture.setBareLandProduction(Double.parseDouble(excel.get(i).get(bare_land_productionLocation)));
                            }else{
                                agriculture.setBareLandProduction(0.0);
                            }
                            if(excel.get(i).get(mulch_wueLocation) != null && !excel.get(i).get(mulch_wueLocation).equals("无")){
                                agriculture.setMulchWue(Double.parseDouble(excel.get(i).get(mulch_wueLocation)));
                            }else{
                                agriculture.setMulchWue(0.0);
                            }
                            if(excel.get(i).get(average_daily_temperatureLocation) != null && !excel.get(i).get(average_daily_temperatureLocation).equals("无")){
                                agriculture.setAverageDailyTemperature(Double.parseDouble(excel.get(i).get(average_daily_temperatureLocation)));
                            }else{
                                agriculture.setAverageDailyTemperature(0.0);
                            }
                            agriculture.setArticleName(excel.get(i).get(article_nameLocation));
                            agriculture.setAuthor(excel.get(i).get(authorLocation));
                            agriculture.setMembraneColor(excel.get(i).get(membrane_colorLocation));
                            if(excel.get(i).get(mulch_yield_wueLocation) != null && !excel.get(i).get(mulch_yield_wueLocation).equals("无")){
                                agriculture.setMulchYieldWue(Double.parseDouble(excel.get(i).get(mulch_yield_wueLocation)));
                            }else{
                                agriculture.setMulchYieldWue(0.0);
                            }

                            agriculture.insert();
                            break;
                        }
                    }
                }
                return "success";
            }catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        return null;
    }

    @Override
    public boolean printExcel() {
        String[] title = {"作物类型", "实验年份", "区域", "试验地点", "经度", "纬度", "海拔高度m", "年均温℃",
                "日照时数h", "≥10℃活动积温℃", "年降水量mm", "无霜期", "土壤质地", "0-100cm土壤容重g/cm3", "地膜类型",
                "地膜颜色", "地膜覆盖方式", "重复数", "地膜覆盖产量kg/hm2", "裸地产量kg/hm2", "地膜覆盖WUE(kg/m3)",
                "裸地WUE(kg/m3)", "生育期土壤5-25cm日均增温℃", "文章名称", "作者"};
        List<String> titles = new ArrayList<>();
        for (String str: title) {
            titles.add(str);
        }
        String tempPath =System.getProperty("java.io.tmpdir")+ File.separator;
        List<List<String>> list = new ArrayList<>();

        List<Agriculture> agricultureList = selectList(null);
        for (Agriculture agriculture : agricultureList) {
            List<String> detailAgriculture = new ArrayList<>();
            detailAgriculture.add(agriculture.getAgroType());
            detailAgriculture.add(agriculture.getYear().toString());
            detailAgriculture.add(agriculture.getArea());
            detailAgriculture.add(agriculture.getTestLocation());
            detailAgriculture.add(agriculture.getLongitude().toString());
            detailAgriculture.add(agriculture.getLatitude().toString());
            detailAgriculture.add(agriculture.getAltitude().toString());
            detailAgriculture.add(agriculture.getAverageAnnualTemperature().toString());
            detailAgriculture.add(agriculture.getSunshineDuration().toString());
            detailAgriculture.add(agriculture.getActiveAccumulatedTemperature().toString());
            detailAgriculture.add(agriculture.getAnnualPrecipitation().toString());
            detailAgriculture.add(agriculture.getFrostFreeSeason().toString());
            detailAgriculture.add(agriculture.getSoilTexture());
            detailAgriculture.add(agriculture.getSoilBulkDensity().toString());
            detailAgriculture.add(agriculture.getMembraneType());
            detailAgriculture.add(agriculture.getMembraneColor());
            detailAgriculture.add(agriculture.getMulchMethod());
            detailAgriculture.add(agriculture.getNumberOfReplication().toString());
            detailAgriculture.add(agriculture.getMulchYield().toString());
            detailAgriculture.add(agriculture.getBareLandProduction().toString());
            detailAgriculture.add(agriculture.getMulchYieldWue().toString());
            detailAgriculture.add(agriculture.getMulchWue().toString());
            detailAgriculture.add(agriculture.getAverageDailyTemperature().toString());
            detailAgriculture.add(agriculture.getArticleName());
            detailAgriculture.add(agriculture.getAuthor());
            list.add(detailAgriculture);
        }
        System.out.println(tempPath);
        String filePath = ExcelUtil.createExcel(list, titles.toArray(),tempPath);
        System.out.println(filePath.substring(filePath.lastIndexOf("\\")+1));
        return false;
    }

    public void download(String filePath,HttpServletResponse response) throws Exception {
        System.out.println(filePath);
        BufferedInputStream bis;
        BufferedOutputStream bos;
        String tempPath =System.getProperty("java.io.tmpdir")+ File.separator;
        filePath = tempPath+filePath;
        File file = new File(filePath);
        InputStream is = new FileInputStream(file);

        response.reset();
        /*response.setContentType("application/vnd.ms-excel;charset=utf-8");*/
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String((filePath + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        bis = new BufferedInputStream(is);
        bos = new BufferedOutputStream(out);
        byte[] buff = new byte[1024];
        int bytesRead;
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }
        bis.close();
        bos.close();

    }
}
