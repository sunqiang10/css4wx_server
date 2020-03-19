package com.kensun.video4wx.utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;

public class FileToolUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileToolUtil.class);
    /**
     * @author cjy
     * @date 2018/6/5 14:35
     * @param file
     * @return
     */
// 判断文件夹是否存在
    public static void judeDirExists(File file) {

        if (file.exists()) {
            if (file.isDirectory()) {
                System.out.println("dir exists");
            } else {
                System.out.println("the same name file exists, can not create dir");
            }
        } else {
            System.out.println("dir not exists, create it ...");
            file.mkdir();
        }

    }
}
