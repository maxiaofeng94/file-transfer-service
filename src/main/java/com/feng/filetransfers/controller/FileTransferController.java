package com.feng.filetransfers.controller;

import com.feng.filetransfers.common.annotation.NotResponseBody;
import com.feng.filetransfers.model.vo.FileStatusVO;
import com.feng.filetransfers.service.IFileTransferService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 文件传输相关接口Controller层
 */
@RestController
@RequestMapping("file")
public class FileTransferController {

    @Autowired
    private IFileTransferService fileTransferService;

    /**
     * 获取文件存在状态
     * @param fileHashCode 文件哈希值
     * @param fileName 文件名称
     * @return 文件状态对象
     */
    @GetMapping("getStatus")
    public FileStatusVO getFileStatus(@RequestParam String fileHashCode, @RequestParam String fileName) {
        return fileTransferService.getFileStatus(fileHashCode, fileName);
    }

    /**
     * 上传分块文件
     * @param file 分块文件
     * @param transferId 文件传输Id
     * @param blockNum 分块文件序号
     * @param blockHashCode 分块文件哈希值
     */
    @PostMapping("uploadBlock")
    public void uploadBlockFile(@RequestParam MultipartFile file, @RequestParam String transferId,
                                @RequestParam int blockNum, @RequestParam String blockHashCode) throws IOException {
        fileTransferService.uploadBlockFile(file, transferId, blockNum, blockHashCode);
    }

    /**
     * 合并分块文件
     * @param transferId 文件传输Id
     * @param fileHashCode 完整文件哈希值
     * @param fileName 文件名称
     * @return 文件Id
     */
    @PostMapping("merge")
    public String mergeFile(@RequestParam String transferId, @RequestParam String fileHashCode,
                          @RequestParam String fileName){
        return fileTransferService.mergeFile(transferId, fileHashCode, fileName);
    }

    /**
     * 下载文件
     * @param fileId 文件Id
     * @param response web响应
     * @throws IOException IO异常
     */
    @GetMapping("download/{fileId}")
    public void downloadFile(@PathVariable String fileId, HttpServletResponse response) throws IOException {
        fileTransferService.downloadFile(fileId, response);
    }
}
