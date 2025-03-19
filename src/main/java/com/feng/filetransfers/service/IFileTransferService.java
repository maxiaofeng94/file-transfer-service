package com.feng.filetransfers.service;

import com.feng.filetransfers.model.vo.FileStatusVO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件传输逻辑实现Service层
 */
public interface IFileTransferService {
    /**
     * 获取文件存在状态
     * @param fileHashCode 文件哈希值
     * @param fileName 文件名称
     * @return 文件存在状态对象
     */
    FileStatusVO getFileStatus(String fileHashCode, String fileName);

    /**
     * 上传分块文件
     * @param file 分块文件
     * @param transferId 文件传输Id
     * @param blockNum 分块文件序号
     * @param blockHashCode 分块文件哈希值
     */
    void uploadBlockFile(MultipartFile file, String transferId, int blockNum, String blockHashCode) throws IOException;

    /**
     * 合并分块文件
     * @param transferId 文件传输Id
     * @param fileHashCode 完整文件哈希值
     * @param fileName 文件名称
     * @return 文件Id
     */
    String mergeFile(String transferId, String fileHashCode, String fileName);

    /**
     * 下载文件
     * @param fileId 文件Id值
     * @param response web响应
     */
    void downloadFile(String fileId, HttpServletResponse response) throws IOException;
}
