package com.huwei.newsdemo.biz.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.huwei.newsdemo.biz.dao.NewsDao;
import com.huwei.newsdemo.biz.entity.Class;
import com.huwei.newsdemo.biz.entity.*;
import com.huwei.newsdemo.biz.service.*;
import com.huwei.newsdemo.util.CreateHtmlUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huwei
 * @since 2019-08-26
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsDao, News> implements INewsService {

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IRoleDeptService roleDeptService;

    @Autowired
    private IDeptClassService deptClassService;

    @Autowired
    private INewsClassService newsClassService;

    //图片路径
    String imageFilePath = this.getClass().getClassLoader().getResource(".").getPath().replace("/target/classes/","/uploadImage/");

    //静态化文件路径
    String staticFilePath = this.getClass().getClassLoader().getResource(".").getPath().replace("/target/classes/","/staticHtmlPath");
    String classPath = "";
    List<String> str = new ArrayList<>();
    //获取分类信息的全路径
    public List<String> findAllPath(int[] classId){
        //获取当前新闻的路径，面包屑导航
        Class clazz = new Class();
        for (int id : classId) {
            clazz = clazz.selectById(id);
            if(!classPath.equals("")){
                classPath = clazz.getName()+"-"+classPath;
            }else{
                classPath = clazz.getName();
            }
            if(clazz.getParentId() != 0){
                int[] a = {clazz.getParentId()};
                findAllPath(a);
            }
            if("".equals(classPath)){

            }else{
                str.add(classPath);
            }

            classPath = "";
        }
        return str;
    }

    // 网页静态化方法

    /**
     *
     * @param title
     * @param content
     * @param allPath
     * @param pageTitle
     * @param newsId
     * @param endName 用来同一个网页在不同分类下的静态化网页
     * @return
     */
    public void staticHtml(String title, String content,String allPath,String pageTitle,int newsId,int endName){
        Map<String, Object> map = new HashMap<>();
        map.put("newsTitle", title);
        map.put("newsContent", content);
        //清空str列表中的值
        str.clear();
        map.put("path",allPath);
        map.put("pageTitle","news"+ pageTitle);
        try {
            CreateHtmlUtils.createHtml("news.ftl",staticFilePath+"/"+"news"+newsId+"_"+endName+".html", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String addNews(News news,int[] classId) {
        EntityWrapper<News> wrapper = new EntityWrapper<>();
        wrapper.where("del_flag != {0}",1).and().where("news_title = {0}",news.getNewsTitle());
        boolean result = news.insert();
        //同时静态化网页,分类多选，即有多个分类，则生成多个静态化网页
        for (int id : classId) {
            String allPath = "";
            int[] a = {id};
            for (String s : findAllPath(a)) {
                allPath += s + "<br>";
            }
            staticHtml(news.getNewsTitle(),news.getNewsContent(),allPath,news.getNewsTitle(),news.getNewsId(),id);
        }

        // 先上传新闻，再新增新闻分类表的记录
        boolean result2 = true;
        NewsClass newsClass = new NewsClass();
        for (int id : classId) {
            newsClass.setNewsId(news.getNewsId());
            newsClass.setNewsClassId(id);
            result2 = result2 && newsClass.insert();
        }

        return result && result2 ? "add success" : "add fail";
    }

    public Page<News> queryByName(String newsKeyWord, int userId,int page,int count){

        List<News> newsList = queryAllByUserId(userId);
        List<News> keyWordNewsList = new ArrayList<>();
        if (newsList != null) {
            for (News news : newsList) {
                if(news.getNewsTitle().contains(newsKeyWord) || news.getNewsContent().contains(newsKeyWord)) {
                    if(keyWordNewsList.size() < 10){
                        keyWordNewsList.add(news);
                    }
                }
            }
        }
        Collections.sort(keyWordNewsList);
        Page<News> newsPage = new Page<>(page,count);
        List<News> newsList2 = new ArrayList<>();
        if (page * count > keyWordNewsList.size()) {
            newsList2 = keyWordNewsList.subList((page - 1) * count,keyWordNewsList.size());
        }else{
            newsList2 = keyWordNewsList.subList((page - 1) * count,(page - 1) * count + count);
        }
        newsPage.setRecords(newsList2);
        newsPage.setTotal(keyWordNewsList.size());
        return newsPage;
    }

    public List<News> queryAllByUserId(int userId){
        List<News> newsList = new ArrayList<>();
        //通过userId查询出角色，通过角色获得所属部门，通过部门获取新闻分类，通过分类获取新闻
        EntityWrapper<UserRole> userRoleWrapper = new EntityWrapper<>();
        userRoleWrapper.where("user_id = {0}",userId);
        List<UserRole> userRoleList = userRoleService.selectList(userRoleWrapper);
        if(userRoleList != null){
            for (UserRole userRole : userRoleList) {
                int roleId = userRole.getRoleId();
                EntityWrapper<RoleDept> roleDeptEntityWrapper = new EntityWrapper<>();
                roleDeptEntityWrapper.where("role_id = {0}",roleId);
                List<RoleDept> roleDeptList = roleDeptService.selectList(roleDeptEntityWrapper);
                if (roleDeptList != null) {
                    for (RoleDept roleDept : roleDeptList) {
                        int deptId = roleDept.getDeptId();
                        EntityWrapper<DeptClass> deptClassEntityWrapper = new EntityWrapper<>();
                        deptClassEntityWrapper.where("dept_id = {0}",deptId);
                        List<DeptClass> deptClassList = deptClassService.selectList(deptClassEntityWrapper);
                        if (deptClassList != null) {
                            for (DeptClass deptClass : deptClassList) {
                                int classId = deptClass.getClassId();
                                EntityWrapper<NewsClass> newsClassEntityWrapper = new EntityWrapper<>();
                                newsClassEntityWrapper.where("news_class_id = {0}",classId);
                                List<NewsClass> newsClassList = newsClassService.selectList(newsClassEntityWrapper);
                                if (newsClassList != null) {
                                    for (NewsClass newsClass : newsClassList) {
                                        int newsId = newsClass.getNewsId();
                                        EntityWrapper<News> wrapper = new EntityWrapper<>();
                                        wrapper.where("news_id = {0}",newsId).and().where("del_flag != {0}",1);
                                        List<News> newsList1 = this.selectList(wrapper);
                                        if(this.selectList(wrapper) != null){
                                            News news = newsList1.get(0);
                                            if(!newsList.contains(news)){
                                                newsList.add(news);
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
        Collections.sort(newsList);
        return newsList;
    }

    public Page<News> queryByPage(int page,int count, int userId){
        Page<News> newsPage = new Page<>(page,count);
        List<News> newsList = queryAllByUserId(userId);
        List<News> newsList2 = new ArrayList<>();
        if (page * count > newsList.size()) {
            newsList2 = newsList.subList((page - 1) * count,newsList.size());
        }else{
            newsList2 = newsList.subList((page - 1) * count,(page - 1) * count + count);
        }

        newsPage.setRecords(newsList2);
        newsPage.setTotal(newsList.size());
        return newsPage;
    }

    @Override
    public Page<News> queryByUserIdAndClassId(int userId, int classId, int page, int count) {
        Page<News> newsPage = new Page<>(page,count);
        List<News> newsList = new ArrayList<>();
        if(classId == 0){
            newsList = queryAllByUserId(userId);
        }else{
            //通过部门获取新闻分类，通过分类获取新闻
            EntityWrapper<NewsClass> newsClassEntityWrapper = new EntityWrapper<>();
            newsClassEntityWrapper.where("news_class_id = {0}",classId);
            List<NewsClass> newsClassList = newsClassService.selectList(newsClassEntityWrapper);
            if (newsClassList != null) {
                for (NewsClass newsClass : newsClassList) {
                    News news = this.selectById(newsClass.getNewsId());
                    if (!newsList.contains(news)) {
                        newsList.add(news);
                    }
                }
            }
        }

        List<News> newsList2 = new ArrayList<>();
        if (page * count > newsList.size()) {
            newsList2 = newsList.subList((page - 1) * count,newsList.size());
        }else{
            newsList2 = newsList.subList((page - 1) * count,(page - 1) * count + count);
        }

        newsPage.setRecords(newsList2);
        newsPage.setTotal(newsList.size());
        return newsPage;
    }


    /**
     * 富文本编辑器图片上传
     * @param file
     * @return
     */
    public String ckeditorUpload(@RequestParam("upload") MultipartFile file, String CKEditorFuncNum) throws Exception {
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //实际处理肯定是要加上一段唯一的字符串（如现在时间），这里简单加 cun
        String newFileName = "cun" + suffixName;
        //使用架包 common-io实现图片上传
        FileUtils.copyInputStreamToFile(file.getInputStream(), new File(imageFilePath + newFileName));
        JSONObject result = new JSONObject();
        result.put("uploaded", 1);

        result.put("fileName", fileName);

        result.put("url", "/file/"+newFileName);
        return result.toString();
    }

    @Override
    public boolean update(News news, int[] classId) {
        // 先修改新闻，再修改新闻分类表的记录,先判断分类表中是否有这条记录，没有就新增，有则修改
        boolean result = news.updateById();
        // 说明本次请求未修改分类信息
        if(classId == null){
            NewsClass newsClass = new NewsClass();
            EntityWrapper<NewsClass> wrapper = new EntityWrapper<>();
            wrapper.where("news_id = {0}",news.getNewsId());
            List<NewsClass> newsClassList = newsClass.selectList(wrapper);
            if(newsClassList.size() != 0){
                for (NewsClass aClass : newsClassList) {
                    //更新静态化网页
                    String allPath = "";
                    int[] a = {aClass.getNewsClassId()};
                    for (String s : findAllPath(a)) {
                        allPath += s + "<br>";
                    }
                    staticHtml(news.getNewsTitle(),news.getNewsContent(),allPath,news.getNewsTitle(),news.getNewsId(),aClass.getNewsClassId());
                }
            }
        }else{
            NewsClass newsClass = new NewsClass();
            EntityWrapper<NewsClass> wrapper = new EntityWrapper<>();
            wrapper.where("news_id = {0}",news.getNewsId());
            List<NewsClass> newsClassList = newsClass.selectList(wrapper);
            if(newsClassList.size() != 0){
                for (NewsClass aClass : newsClassList) {
                    aClass.deleteById();
                    //同时删除原本的静态化网页
                    File file = new File(staticFilePath+"/news"+news.getNewsId()+"_"+aClass.getNewsClassId()+".html");
                    file.delete();
                }
            }
            for (int id : classId) {
                newsClass.setNewsId(news.getNewsId());
                newsClass.setNewsClassId(id);
                newsClass.insert();

                //更新静态化网页
                String allPath = "";
                int[] a = {id};
                for (String s : findAllPath(a)) {
                    allPath += s + "<br>";
                }
                staticHtml(news.getNewsTitle(),news.getNewsContent(),allPath,news.getNewsTitle(),news.getNewsId(),id);
            }
        }

        return true;
    }

    @Override
    public boolean delete(int newsId) {
        //删除时，同步删除新闻-分类表中关联数据
        NewsClass newsClass = new NewsClass();
        EntityWrapper<NewsClass> wrapper = new EntityWrapper<>();
        wrapper.where("news_id = {0}",newsId);
        List<NewsClass> newsClassList = newsClass.selectList(wrapper);
        if(newsClassList.size() != 0){
            for (NewsClass aClass : newsClassList) {
                aClass.deleteById();
                //同时删除原本的静态化网页
                File file = new File("F:\\IdeaWorkSpace\\html\\news"+newsId+"_"+aClass.getNewsClassId()+".html");
                file.delete();
            }
        }
        EntityWrapper<News> newsWrapper = new EntityWrapper<>();
        newsWrapper.where("news_id = {0}",newsId).and().where("del_flag != {0}",1);
        List<News> newsList = selectList(newsWrapper);
        if (newsList != null) {
            News news = newsList.get(0);
            news.setDelFlag(1);
            news.updateById();
        }
        return true;
    }
}
