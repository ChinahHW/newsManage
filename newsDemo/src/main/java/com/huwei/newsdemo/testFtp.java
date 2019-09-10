package com.huwei.newsdemo;

import com.huwei.newsdemo.util.FtpUtils;

import java.io.*;

public class testFtp {
    public static void main(String[] args) {
        File file = new File("F:\\IdeaWorkSpace\\newsDemo\\src\\main\\resources\\static\\news22.html");
        try {
            InputStream is = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len = -1;
            while((len = is.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            FtpUtils.sshSftp(bos.toByteArray(),"news22.html");
            bos.close();
            is.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
