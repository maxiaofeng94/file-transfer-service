package com.feng.filetransfers.util;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 文件操作通用类
 */
public class FileUtil {
    /**
     * 计算文件的SHA256哈希值
     * @param file 文件
     * @return 文件的SHA256哈希值
     */
    public static String calcSha256Hash(File file) throws NoSuchAlgorithmException, IOException {
        // 创建 MessageDigest 实例，指定算法为 SHA-256
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        // 读取文件并更新哈希值
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[8192]; // 8KB 缓冲区
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
        }

        // 返回最终的哈希值
        return bytesToHex(digest.digest());
    }

    /**
     * 将字节数组转换为十六进制字符串
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
