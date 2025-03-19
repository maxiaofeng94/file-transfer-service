package com.feng.filetransfers.dao.impl;

import com.feng.filetransfers.dao.IFileStorageDao;
import com.feng.filetransfers.model.dto.BlockFileInfoDTO;
import com.feng.filetransfers.model.dto.FileInfoDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.OutputStream;
import java.util.List;

@Repository
@ConditionalOnProperty(name="file-transfer.storage-source", havingValue = "minIO", matchIfMissing = true)
public class MinIOFileStorageDao implements IFileStorageDao {
    @Override
    public String generateTransferId() {
        return "";
    }

    @Override
    public String saveBlockFile(String transferId, int blockNum, File file) {
        return "";
    }

    @Override
    public FileInfoDTO saveMergeFile(String transferId, String fileHashCode, List<BlockFileInfoDTO> blockInfos) {
        return null;
    }

    @Override
    public void downloadFile(String storageId, OutputStream outputStream) {

    }
}
