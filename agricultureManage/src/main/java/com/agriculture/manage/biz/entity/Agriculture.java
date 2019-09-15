package com.agriculture.manage.biz.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author huwei
 * @since 2019-09-15
 */
@Data
@Accessors(chain = true)
public class Agriculture extends Model<Agriculture> {

    private static final long serialVersionUID = 1L;

    /**
     * 作物id
     */
    @TableId(value = "agro_id", type = IdType.AUTO)
    private Integer agroId;
    /**
     * 作物类型
     */
    @TableField("agro_type")
    private String agroType;
    /**
     * 实验年份
     */
    @TableField("year")
    private Integer year;
    /**
     * 区域
     */
    @TableField("area")
    private String area;
    /**
     * 试验地点
     */
    @TableField("test_location")
    private String testLocation;
    /**
     * 经度
     */
    @TableField("longitude")
    private Double longitude;
    /**
     * 纬度
     */
    @TableField("latitude")
    private Double latitude;
    /**
     * 海拔高度（m）
     */
    @TableField("altitude")
    private Double altitude;
    /**
     * 年均温
     */
    @TableField("average_annual_temperature")
    private Double averageAnnualTemperature;
    /**
     * 日照时数h
     */
    @TableField("sunshine_duration")
    private Double sunshineDuration;
    /**
     * ≥10℃活动积温℃
     */
    @TableField("active_accumulated_temperature")
    private Double activeAccumulatedTemperature;
    /**
     * 年降水量
     */
    @TableField("annual_precipitation")
    private Double annualPrecipitation;
    /**
     * 无霜期
     */
    @TableField("frost_free_season")
    private Double frostFreeSeason;
    /**
     * 土壤质地
     */
    @TableField("soil_texture")
    private String soilTexture;
    /**
     * 0-100cm土壤容重g/cm3
     */
    @TableField("soil_bulk_density")
    private Double soilBulkDensity;
    /**
     * 地膜类型
     */
    @TableField("membrane_type")
    private String membraneType;
    /**
     * 地膜覆盖方式
     */
    @TableField("mulch_method")
    private String mulchMethod;
    /**
     * 重复数
     */
    @TableField("number_of_replication")
    private Integer numberOfReplication;
    /**
     * 地膜覆盖产量
     */
    @TableField("mulch_yield")
    private Double mulchYield;
    /**
     * 裸地产量
     */
    @TableField("bare_land_production")
    private Double bareLandProduction;
    /**
     * 裸地WUE(kg/m3)
     */
    @TableField("mulch_wue")
    private Double mulchWue;
    /**
     * 生育期土壤5-25cm日均增温℃
     */
    @TableField("average_daily_temperature")
    private Double averageDailyTemperature;
    /**
     * 文章名称
     */
    @TableField("article_name")
    private String articleName;
    /**
     * 作者
     */
    @TableField("author")
    private String author;
    /**
     * 地膜颜色
     */
    @TableField("membrane_color")
    private String membraneColor;
    /**
     * 地膜覆盖WUE(kg/m3)
     */
    @TableField("mulch_yield_wue")
    private Double mulchYieldWue;


    public static final String AGRO_ID = "agro_id";

    public static final String AGRO_TYPE = "agro_type";

    public static final String YEAR = "year";

    public static final String AREA = "area";

    public static final String TEST_LOCATION = "test_location";

    public static final String LONGITUDE = "longitude";

    public static final String LATITUDE = "latitude";

    public static final String ALTITUDE = "altitude";

    public static final String AVERAGE_ANNUAL_TEMPERATURE = "average_annual_temperature";

    public static final String SUNSHINE_DURATION = "sunshine_duration";

    public static final String ACTIVE_ACCUMULATED_TEMPERATURE = "active_accumulated_temperature";

    public static final String ANNUAL_PRECIPITATION = "annual_precipitation";

    public static final String FROST_FREE_SEASON = "frost_free_season";

    public static final String SOIL_TEXTURE = "soil_texture";

    public static final String SOIL_BULK_DENSITY = "soil_bulk_density";

    public static final String MEMBRANE_TYPE = "membrane_type";

    public static final String MULCH_METHOD = "mulch_method";

    public static final String NUMBER_OF_REPLICATION = "number_of_replication";

    public static final String MULCH_YIELD = "mulch_yield";

    public static final String BARE_LAND_PRODUCTION = "bare_land_production";

    public static final String MULCH_WUE = "mulch_wue";

    public static final String AVERAGE_DAILY_TEMPERATURE = "average_daily_temperature";

    public static final String ARTICLE_NAME = "article_name";

    public static final String AUTHOR = "author";

    public static final String MEMBRANE_COLOR = "membrane_color";

    public static final String MULCH_YIELD_WUE = "mulch_yield_wue";

    @Override
    public String toString() {
        return "Agriculture{" +
                "agroId=" + agroId +
                ", agroType='" + agroType + '\'' +
                ", year=" + year +
                ", area='" + area + '\'' +
                ", testLocation='" + testLocation + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", altitude=" + altitude +
                ", averageAnnualTemperature=" + averageAnnualTemperature +
                ", sunshineDuration=" + sunshineDuration +
                ", activeAccumulatedTemperature=" + activeAccumulatedTemperature +
                ", annualPrecipitation=" + annualPrecipitation +
                ", frostFreeSeason=" + frostFreeSeason +
                ", soilTexture='" + soilTexture + '\'' +
                ", soilBulkDensity=" + soilBulkDensity +
                ", membraneType='" + membraneType + '\'' +
                ", mulchMethod='" + mulchMethod + '\'' +
                ", numberOfReplication=" + numberOfReplication +
                ", mulchYield=" + mulchYield +
                ", bareLandProduction=" + bareLandProduction +
                ", mulchWue=" + mulchWue +
                ", averageDailyTemperature=" + averageDailyTemperature +
                ", articleName='" + articleName + '\'' +
                ", author='" + author + '\'' +
                ", membraneColor='" + membraneColor + '\'' +
                ", mulchYieldWue=" + mulchYieldWue +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getAgroId() {
        return agroId;
    }

    public void setAgroId(Integer agroId) {
        this.agroId = agroId;
    }

    public String getAgroType() {
        return agroType;
    }

    public static String getYEAR() {
        return YEAR;
    }

    public static String getAREA() {
        return AREA;
    }

    public void setAgroType(String agroType) {
        this.agroType = agroType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTestLocation() {
        return testLocation;
    }

    public static String getLONGITUDE() {
        return LONGITUDE;
    }

    public static String getLATITUDE() {
        return LATITUDE;
    }

    public static String getALTITUDE() {
        return ALTITUDE;
    }

    public void setTestLocation(String testLocation) {
        this.testLocation = testLocation;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public Double getAverageAnnualTemperature() {
        return averageAnnualTemperature;
    }

    public void setAverageAnnualTemperature(Double averageAnnualTemperature) {
        this.averageAnnualTemperature = averageAnnualTemperature;
    }

    public Double getSunshineDuration() {
        return sunshineDuration;
    }

    public void setSunshineDuration(Double sunshineDuration) {
        this.sunshineDuration = sunshineDuration;
    }

    public Double getActiveAccumulatedTemperature() {
        return activeAccumulatedTemperature;
    }

    public void setActiveAccumulatedTemperature(Double activeAccumulatedTemperature) {
        this.activeAccumulatedTemperature = activeAccumulatedTemperature;
    }

    public Double getAnnualPrecipitation() {
        return annualPrecipitation;
    }

    public void setAnnualPrecipitation(Double annualPrecipitation) {
        this.annualPrecipitation = annualPrecipitation;
    }

    public Double getFrostFreeSeason() {
        return frostFreeSeason;
    }

    public void setFrostFreeSeason(Double frostFreeSeason) {
        this.frostFreeSeason = frostFreeSeason;
    }

    public String getSoilTexture() {
        return soilTexture;
    }

    public void setSoilTexture(String soilTexture) {
        this.soilTexture = soilTexture;
    }

    public Double getSoilBulkDensity() {
        return soilBulkDensity;
    }

    public void setSoilBulkDensity(Double soilBulkDensity) {
        this.soilBulkDensity = soilBulkDensity;
    }

    public String getMembraneType() {
        return membraneType;
    }

    public void setMembraneType(String membraneType) {
        this.membraneType = membraneType;
    }

    public String getMulchMethod() {
        return mulchMethod;
    }

    public void setMulchMethod(String mulchMethod) {
        this.mulchMethod = mulchMethod;
    }

    public Integer getNumberOfReplication() {
        return numberOfReplication;
    }

    public void setNumberOfReplication(Integer numberOfReplication) {
        this.numberOfReplication = numberOfReplication;
    }

    public Double getMulchYield() {
        return mulchYield;
    }

    public void setMulchYield(Double mulchYield) {
        this.mulchYield = mulchYield;
    }

    public Double getBareLandProduction() {
        return bareLandProduction;
    }

    public void setBareLandProduction(Double bareLandProduction) {
        this.bareLandProduction = bareLandProduction;
    }

    public Double getMulchWue() {
        return mulchWue;
    }

    public void setMulchWue(Double mulchWue) {
        this.mulchWue = mulchWue;
    }

    public Double getAverageDailyTemperature() {
        return averageDailyTemperature;
    }

    public void setAverageDailyTemperature(Double averageDailyTemperature) {
        this.averageDailyTemperature = averageDailyTemperature;
    }

    public String getArticleName() {
        return articleName;
    }

    public static String getAUTHOR() {
        return AUTHOR;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMembraneColor() {
        return membraneColor;
    }

    public void setMembraneColor(String membraneColor) {
        this.membraneColor = membraneColor;
    }

    public Double getMulchYieldWue() {
        return mulchYieldWue;
    }

    public void setMulchYieldWue(Double mulchYieldWue) {
        this.mulchYieldWue = mulchYieldWue;
    }

    @Override
    protected Serializable pkVal() {
        return this.agroId;
    }

}
