package com.feng.filetransfers.dao.impl;

import com.feng.filetransfers.model.dto.BlockFileInfoDTO;
import com.feng.filetransfers.model.dto.FileInfoDTO;
import com.feng.filetransfers.model.dto.FileNameDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MongodbFileMetaInfoDaoTest {

    @Autowired
    private MongodbFileMetaInfoDao dao;

    @Test
    void getFileInfoByHashCode() {
        FileInfoDTO fileInfo = dao.getFileInfoByHashCode("123");
        assertTrue(true);
    }

    @Test
    void saveFileInfo() {
        FileInfoDTO fileInfoDTO = new FileInfoDTO(
                "123", 100, "123", LocalDateTime.now()
        );
        dao.saveFileInfo(fileInfoDTO);
    }

    @Test
    void getFileNameByFileId() {
        FileNameDTO fileName = dao.getFileNameByFileId("123");
        assertTrue(true);
    }

    @Test
    void queryFileName() {
        FileNameDTO fileName1 = dao.queryFileName("123", "abc", "txt");

        FileNameDTO fileName2 = dao.queryFileName("123", "abc1", "txt");

        assertTrue(true);
    }

    @Test
    void saveFileName() {
        FileNameDTO fileInfoDTO = new FileNameDTO("123", "123", "abc.txt", LocalDateTime.now());
        dao.saveFileName(fileInfoDTO);
    }

    @Test
    void saveBlockFileInfo() {
        BlockFileInfoDTO blockFileInfoDTO = new BlockFileInfoDTO("1234", "123",
                "123", 1, "abc", LocalDateTime.now());
        dao.saveBlockFileInfo(blockFileInfoDTO);
    }

    @Test
    void getBlockFileInfosByTransferId() {
        List<BlockFileInfoDTO> infos = dao.getBlockFileInfosByTransferId("123");
        assertTrue(true);
    }
}