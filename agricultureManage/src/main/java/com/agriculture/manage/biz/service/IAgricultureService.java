package com.agriculture.manage.biz.service;

import com.agriculture.manage.biz.entity.Agriculture;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huwei
 * @since 2019-09-14
 */
public interface IAgricultureService extends IService<Agriculture> {

    List<Agriculture> queryAll();

    Page<Agriculture> queryByKeyWord(String keyWord);

    String add(Agriculture agriculture, int[] permission, int[] depts);

    boolean update(Agriculture agriculture, int[] permission, int[] depts);

    Page<Agriculture> queryByPage(int page, int count);

    boolean delete(Agriculture agriculture);

    String upload(MultipartFile file,
                  HttpServletRequest request, HttpServletResponse response);

    boolean printExcel();

    void download(String filePath,HttpServletResponse response) throws Exception;
}
