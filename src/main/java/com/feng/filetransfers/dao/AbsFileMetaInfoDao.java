package com.feng.filetransfers.dao;

import com.feng.filetransfers.model.dto.BlockFileInfoDTO;
import com.feng.filetransfers.model.dto.FileFullInfoDTO;
import com.feng.filetransfers.model.dto.FileInfoDTO;
import com.feng.filetransfers.model.dto.FileNameDTO;

import java.util.List;

/**
 * 文件元信息操作抽象类
 */
public abstract class AbsFileMetaInfoDao {
    /**
     * 根据文件哈希值获取文件信息
     * @param fileHashCode 文件哈希值
     * @return 文件信息对象，如果没有找到，则返回null
     */
    public abstract FileInfoDTO getFileInfoByHashCode(String fileHashCode);

    /**
     * 保存文件信息
     * @param fileInfo 文件信息对象
     */
    public abstract void saveFileInfo(FileInfoDTO fileInfo);

    /**
     * 根据文件Id获取文件名称
     * @param fileId 文件Id
     * @return 文件名称对象，如果没有找到，则返回null
     */
    public abstract FileNameDTO getFileNameByFileId(String fileId);

    /**
     * 根据文件哈希值和文件完整名称查询对应的文件名称对象
     * @param fileHashCode 文件哈希值
     * @param name 文件名称（不含后缀）
     * @param ext 文件后缀（不含“.”）
     * @return 文件名称对象，如果没有找到，则返回null
     */
    public abstract FileNameDTO queryFileName(String fileHashCode, String name, String ext);

    /**
     * 保存文件名称
     * @param fileName 文件名称对象
     */
    public abstract void saveFileName(FileNameDTO fileName);

    /**
     * 保存分块文件信息
     * @param blockFileInfo 分块文件信息对象
     */
    public abstract void saveBlockFileInfo(BlockFileInfoDTO blockFileInfo);

    /**
     * 根据文件传输Id获取分块文件信息列表
     * @param transferId 文件传输Id
     * @return 分块文件信息列表
     */
    public abstract List<BlockFileInfoDTO> getBlockFileInfosByTransferId(String transferId);

    /**
     * 根据文件传输Id删除分块文件信息
     * @param transferId 文件传输Id
     */
    public abstract void deleteBlockFileInfoByTransferId(String transferId);

    /**
     * 根据文件Id获取文件完整信息
     * @param fileId 文件Id
     * @return 文件完整信息对象，如果没有找到，则返回null
     */
    public FileFullInfoDTO getFileFullInfoByFileId(String fileId) {
        FileNameDTO fileNameDTO = getFileNameByFileId(fileId);
        if(fileNameDTO == null) {
            return null;
        }

        FileInfoDTO fileInfoDTO = getFileInfoByHashCode(fileNameDTO.getHashCode());
        if(fileInfoDTO == null) {
            return null;
        }

        return new FileFullInfoDTO(fileInfoDTO, fileNameDTO);
    }
}
