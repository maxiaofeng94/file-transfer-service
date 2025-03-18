package com.feng.filetransfers.controller;

import com.feng.filetransfers.common.annotation.NotResponseBody;
import com.feng.filetransfers.model.vo.FileStatusVO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件传输相关接口Controller层
 */
@RestController
@RequestMapping("file")
public class FileTransferController {

    @GetMapping("getStatus")
    public FileStatusVO getFileStatus(@RequestParam String fileHashCode, @RequestParam String fileName) {
        return null;
    }

    @PostMapping("uploadBlock")
    public void uploadBlockFile(@RequestParam MultipartFile file, @RequestParam String transferId,
                                @RequestParam int blockNum, @RequestParam String blockHashCode) {

    }

    @PostMapping("merge")
    public void mergeFile(@RequestParam String transferId, @RequestParam String fileHashCode,
                          @RequestParam String fileName){

    }

    @GetMapping("download/{fileId}")
    public void downloadFile(@PathVariable String fileId, HttpServletResponse response) {

    }
}
