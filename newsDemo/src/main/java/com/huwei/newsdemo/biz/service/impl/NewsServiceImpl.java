package com.huwei.newsdemo.biz.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.huwei.newsdemo.biz.dao.NewsDao;
import com.huwei.newsdemo.biz.entity.Class;
import com.huwei.newsdemo.biz.entity.News;
import com.huwei.newsdemo.biz.entity.NewsClass;
import com.huwei.newsdemo.biz.service.INewsService;
import com.huwei.newsdemo.util.CreateHtmlUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
 * @since 2019-08-26
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsDao, News> implements INewsService {

    @Value("${filePath}")
    String path;



    private File upload; // 文件
    private String uploadContentType; // 文件类型
    private String uploadFileName; // 文件名

    // 注意路径格式，一般为项目路径下的一个文件夹里边，项目发布到linux服务器上又得改了
    String imageFilePath = "F:\\IdeaWorkSpace\\newsDemo\\src\\main\\resources\\static\\file\\uploadImage\\";
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
            CreateHtmlUtils.createHtml("news.ftl",path+"/"+"news"+newsId+"_"+endName+".html", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String addNews(News news,int[] classId) {

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

    public List<News> queryByName(String newsKeyWord){
        EntityWrapper<News> qryWrapper = new EntityWrapper<>();
        News news = new News();
        qryWrapper.like("news_title",newsKeyWord).or().like("news_content",newsKeyWord).or().like("news_tag",newsKeyWord).last("LIMIT 12").and().where("del_flag != {0}",1);
        System.out.println(qryWrapper);
        List<News> newsList = news.selectList(qryWrapper);

        return newsList;
    }

    public List<News> queryAll(){
        News news = new News();
        List<News> newsList = news.selectAll();
        return newsList;
    }

    public Page<News> queryByPage(int page,int count){
        Page<News> newsPage = new Page<>(page,count);
        return this.selectPage(newsPage);
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
        //实现图片回显，基本上是固定代码，只需改路劲即可
//        StringBuffer sb = new StringBuffer();
//        sb.append("<script type=\"text/javascript\">");
//        sb.append("window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum + ",'" + "/file/uploadImage/" + newFileName
//                + "','')");
//        sb.append("</script>");
        JSONObject result = new JSONObject();
        result.put("uploaded", 1);

        result.put("fileName", fileName);

        result.put("url", "/file/uploadImage/cun.jpg");
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
                    File file = new File("F:\\IdeaWorkSpace\\html\\news"+news.getNewsId()+"_"+aClass.getNewsClassId()+".html");
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
        return deleteById(newsId);
    }
}
