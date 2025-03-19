package com.feng.filetransfers.dao.impl;

import com.feng.filetransfers.dao.AbsFileMetaInfoDao;
import com.feng.filetransfers.model.dto.BlockFileInfoDTO;
import com.feng.filetransfers.model.dto.FileInfoDTO;
import com.feng.filetransfers.model.dto.FileNameDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MongodbFileMetaInfoDao extends AbsFileMetaInfoDao {
    @Override
    public FileInfoDTO getFileInfoByHashCode(String fileHashCode) {
        return null;
    }

    @Override
    public void saveFileInfo(FileInfoDTO fileInfo) {

    }

    @Override
    public FileNameDTO getFileNameByFileId(String fileId) {
        return null;
    }

    @Override
    public FileNameDTO queryFileName(String fileHashCode, String name, String ext) {
        return null;
    }

    @Override
    public void saveFileName(FileNameDTO fileName) {

    }

    @Override
    public void saveBlockFileInfo(BlockFileInfoDTO blockFileInfo) {

    }

    @Override
    public List<BlockFileInfoDTO> getBlockFileInfosByTransferId(String transferId) {
        return List.of();
    }
}
