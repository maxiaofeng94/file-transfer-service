package com.feng.filetransfers.dao;

import com.feng.filetransfers.model.dto.BlockFileInfoDTO;
import com.feng.filetransfers.model.dto.FileInfoDTO;

import java.io.File;
import java.io.OutputStream;
import java.util.List;

/**
 * 文件存储操作接口类
 */
public interface IFileStorageDao {
    /**
     * 生成文件传输Id
     * @return 文件传输Id
     */
    String generateTransferId(String fileHashCode, String fileName);

    /**
     * 保存分块文件到存储介质中
     * @param transferId 文件传输Id
     * @param fileHashCode 完整文件的哈希值
     * @param blockNum 分块文件序号
     * @param blockFile 分块文件
     * @return 分块文件在存储介质中的Id值
     */
    String saveBlockFile(String transferId, String fileHashCode, int blockNum, File blockFile);

    /**
     * 合并分块文件并将合并后的文件保存至存储介质中
     * @param transferId 文件传输Id
     * @param fileHashCode 完整文件哈希值
     * @param blockInfos 分块文件信息列表
     * @return 合并后文件的文件信息对象
     */
    FileInfoDTO saveMergeFile(String transferId, String fileHashCode, String fileName,
                              List<BlockFileInfoDTO> blockInfos);

    /**
     * 从存储介质中下载文件输出到输出流中
     * @param storageId 文件在存储介质Id值
     * @param outputStream 文件输出流
     */
    void downloadFile(String storageId, OutputStream outputStream);
}
