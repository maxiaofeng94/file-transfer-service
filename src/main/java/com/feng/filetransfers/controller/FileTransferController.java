package com.feng.filetransfers.controller;

import com.feng.filetransfers.model.vo.FileStatusVO;
import com.feng.filetransfers.service.IFileTransferService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * 文件传输相关接口Controller层
 */
@RestController
@RequestMapping("file")
@CrossOrigin(origins = "*")
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
     * @param fileHashCode 完整文件的哈希值
     */
    @PostMapping("uploadBlock")
    public void uploadBlockFile(@RequestParam MultipartFile file, @RequestParam String transferId,
                                @RequestParam int blockNum, @RequestParam String blockHashCode,
                                @RequestParam String fileHashCode) throws IOException, NoSuchAlgorithmException {
        fileTransferService.uploadBlockFile(file, transferId, blockNum, blockHashCode, fileHashCode);
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
     * 中止文件上传
     * @param transferId 文件传输Id
     * @param fileHashCode 完整文件哈希值
     */
    @PostMapping("stop")
    public void stopUpload(@RequestParam String transferId, @RequestParam String fileHashCode){
        fileTransferService.stopUpload(transferId, fileHashCode);
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
