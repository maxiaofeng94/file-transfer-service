package com.feng.filetransfers.util;

import org.springframework.boot.system.ApplicationHome;

import java.io.File;

/**
 * 路径相关操作通用类
 */
public class PathUtil {
    /**
     * 项目所在路径文件对象
     */
    private static final File projectSource;

    static {
        ApplicationHome home = new ApplicationHome(PathUtil.class);
        projectSource = home.getSource();
    }

    /**
     * 获取项目所在目录路径
     * @return 目录路径
     */
    public static String getProjectDir() {
        if(projectSource == null) {
            return "";
        }
        return projectSource.getParent();
    }
}
