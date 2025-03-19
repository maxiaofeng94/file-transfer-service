package com.feng.filetransfers.dao.impl;

import com.feng.filetransfers.model.dto.FileInfoDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MongodbFileMetaInfoDaoTest {

    @Autowired
    private MongodbFileMetaInfoDao dao;

    @Test
    void getFileInfoByHashCode() {
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
    }

    @Test
    void queryFileName() {
    }

    @Test
    void saveFileName() {
    }

    @Test
    void saveBlockFileInfo() {
    }

    @Test
    void getBlockFileInfosByTransferId() {
    }
}