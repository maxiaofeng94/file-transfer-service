package com.feng.filetransfers.dao.impl;

import com.feng.filetransfers.common.exception.APIException;
import com.feng.filetransfers.dao.IFileStorageDao;
import com.feng.filetransfers.model.dto.BlockFileInfoDTO;
import com.feng.filetransfers.model.dto.FileInfoDTO;
import com.feng.filetransfers.properties.MinioProperties;
import io.minio.*;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.XmlParserException;
import io.minio.messages.Part;
import io.minio.messages.Tags;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Repository
@ConditionalOnProperty(name="file-transfer.storage-source", havingValue = "minIO", matchIfMissing = true)
@Slf4j
public class MinIOFileStorageDao implements IFileStorageDao {

    @Autowired
    private MinioAsyncClient minioAsyncClient;

    @Autowired
    private MinioProperties minioProperties;

    @Override
    public String generateTransferId(String fileHashCode, String fileName) {
        try {
            CreateMultipartUploadResponse initResponse = minioAsyncClient.createMultipartUploadAsync(
                    minioProperties.getBucket(), null,
                    fileHashCode, null, null).get();

            return initResponse.result().uploadId();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String saveBlockFile(String transferId, String fileHashCode, int blockNum, File blockFile) {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(blockFile, "r")) {
            UploadPartResponse uploadPartResponse = minioAsyncClient.uploadPartAsync(minioProperties.getBucket(),
                    null, fileHashCode, randomAccessFile, blockFile.length(),
                    transferId, blockNum, null, null).get();
            return uploadPartResponse.etag();
        } catch (IOException | InsufficientDataException | NoSuchAlgorithmException | ExecutionException |
                 InvalidKeyException | InterruptedException | XmlParserException | InternalException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public FileInfoDTO saveMergeFile(String transferId, String fileHashCode, String fileName,
                                     List<BlockFileInfoDTO> blockInfos) {
        List<Part> parts = new ArrayList<>();
        for (BlockFileInfoDTO blockInfo : blockInfos) {
            parts.add(new Part(blockInfo.getNum(), blockInfo.getStorageId()));
        }
        try {
            ObjectWriteResponse completeResponse = minioAsyncClient.completeMultipartUploadAsync(
                    minioProperties.getBucket(), null, fileHashCode, transferId,
                    parts.toArray(new Part[0]), null, null).get();

            //将文件名称记录到tags中
            minioAsyncClient.setObjectTags(SetObjectTagsArgs.builder().
                    bucket(minioProperties.getBucket()).object(fileHashCode)
                    .tags(Map.of("fileName", fileName)).build());

            StatObjectResponse statObjectResponse = minioAsyncClient.statObject(StatObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(fileHashCode).build()).get();

            return new FileInfoDTO(fileHashCode, statObjectResponse.size(), fileHashCode, LocalDateTime.now());
        } catch (InterruptedException | ExecutionException | InsufficientDataException | InternalException |
                 InvalidKeyException | IOException | NoSuchAlgorithmException | XmlParserException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void downloadFile(String storageId, OutputStream outputStream) {
        try {
            GetObjectResponse getObjectResponse = minioAsyncClient.getObject(GetObjectArgs.builder()
                    .bucket(minioProperties.getBucket()).object(storageId).build()).get();

            byte[] buffer = new byte[8192 * 8]; // 64KB
            int read;
            while ((read = getObjectResponse.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            getObjectResponse.close();
        } catch (InterruptedException | ExecutionException | InsufficientDataException | InternalException |
                 InvalidKeyException | IOException | NoSuchAlgorithmException | XmlParserException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
