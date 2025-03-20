package com.feng.filetransfers.dao.impl;

import com.feng.filetransfers.dao.AbsFileMetaInfoDao;
import com.feng.filetransfers.model.dos.mongodb.BlockFileInfoDO;
import com.feng.filetransfers.model.dos.mongodb.FileInfoDO;
import com.feng.filetransfers.model.dos.mongodb.FileNameDO;
import com.feng.filetransfers.model.dto.BlockFileInfoDTO;
import com.feng.filetransfers.model.dto.FileInfoDTO;
import com.feng.filetransfers.model.dto.FileNameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@ConditionalOnProperty(name="file-transfer.meta-info-source", havingValue = "mongodb", matchIfMissing = true)
public class MongodbFileMetaInfoDao extends AbsFileMetaInfoDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public FileInfoDTO getFileInfoByHashCode(String fileHashCode) {
        FileInfoDO fileInfoDO = mongoTemplate.findById(fileHashCode, FileInfoDO.class);

        if(fileInfoDO == null){
            return null;
        }

        return fileInfoDO.toDTO();
    }

    @Override
    public void saveFileInfo(FileInfoDTO fileInfo) {
        FileInfoDO fileInfoDO = new FileInfoDO(fileInfo);
        mongoTemplate.save(fileInfoDO);
    }

    @Override
    public FileNameDTO getFileNameByFileId(String fileId) {
        FileNameDO fileNameDO = mongoTemplate.findById(fileId, FileNameDO.class);
        if(fileNameDO == null){
            return null;
        }

        return fileNameDO.toDTO();
    }

    @Override
    public FileNameDTO queryFileName(String fileHashCode, String name, String ext) {
        Query query = new Query(Criteria.where("hashCode").is(fileHashCode).and("name").is(name).and("ext").is(ext));
        List<FileNameDO> fileNameDOs = mongoTemplate.find(query, FileNameDO.class);
        if(fileNameDOs.isEmpty()){
            return null;
        }

        return fileNameDOs.get(0).toDTO();
    }

    @Override
    public void saveFileName(FileNameDTO fileName) {
        FileNameDO fileNameDO = new FileNameDO(fileName);
        mongoTemplate.save(fileNameDO);
    }

    @Override
    public void saveBlockFileInfo(BlockFileInfoDTO blockFileInfo) {
        BlockFileInfoDO blockFileInfoDO = new BlockFileInfoDO(blockFileInfo);
        mongoTemplate.save(blockFileInfoDO);
    }

    @Override
    public List<BlockFileInfoDTO> getBlockFileInfosByTransferId(String transferId) {
        Query query = new Query(Criteria.where("transferId").is(transferId));
        List<BlockFileInfoDO> blockFileInfoDOs = mongoTemplate.find(query, BlockFileInfoDO.class);

        List<BlockFileInfoDTO> blockFileInfoDTOs = new ArrayList<>();
        for(BlockFileInfoDO blockFileInfoDO : blockFileInfoDOs){
            blockFileInfoDTOs.add(blockFileInfoDO.toDTO());
        }

        return blockFileInfoDTOs;
    }

    @Override
    public void deleteBlockFileInfoByTransferId(String transferId) {
        mongoTemplate.remove(new Query(Criteria.where("transferId").is(transferId)), BlockFileInfoDO.class);
    }
}
