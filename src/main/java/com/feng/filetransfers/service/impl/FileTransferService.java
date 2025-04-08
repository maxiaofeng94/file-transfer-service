package com.feng.filetransfers.service.impl;

import com.feng.filetransfers.common.exception.APIException;
import com.feng.filetransfers.dao.AbsFileMetaInfoDao;
import com.feng.filetransfers.dao.IFileStorageDao;
import com.feng.filetransfers.model.dto.BlockFileInfoDTO;
import com.feng.filetransfers.model.dto.FileFullInfoDTO;
import com.feng.filetransfers.model.dto.FileInfoDTO;
import com.feng.filetransfers.model.dto.FileNameDTO;
import com.feng.filetransfers.model.enums.FileStatus;
import com.feng.filetransfers.model.vo.FileStatusVO;
import com.feng.filetransfers.properties.FileTransferProperties;
import com.feng.filetransfers.service.IFileTransferService;
import com.feng.filetransfers.util.FileUtil;
import com.feng.filetransfers.util.PathUtil;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileTransferService implements IFileTransferService {

    private final AbsFileMetaInfoDao fileMetaInfoDao;

    private final IFileStorageDao fileStorageDao;

    private final FileTransferProperties properties;

    private final String tempPath;

    public FileTransferService(AbsFileMetaInfoDao fileMetaInfoDao, IFileStorageDao fileStorageDao, FileTransferProperties properties) {
        this.fileMetaInfoDao = fileMetaInfoDao;
        this.fileStorageDao = fileStorageDao;
        this.properties = properties;

        // 获取临时路径
        if (properties.getTempPath() == null || properties.getTempPath().isEmpty()) {
            tempPath = PathUtil.getProjectDir() + File.separator + "temp";
        }else{
            File temp = new File(properties.getTempPath());
            if(temp.isAbsolute()){
                tempPath = properties.getTempPath();
            }else{
                tempPath = PathUtil.getProjectDir() + File.separator + properties.getTempPath();
            }
        }
    }

    @Override
    public FileStatusVO getFileStatus(String fileHashCode, String fileName) {
        // 根据文件哈希值获取是否已有对应文件
        FileInfoDTO fileInfoDTO = fileMetaInfoDao.getFileInfoByHashCode(fileHashCode);

        // 没有对应文件，则返回文件不存在状态，并且生成文件传输Id
        if(fileInfoDTO == null) {
            return new FileStatusVO(FileStatus.NONE, fileStorageDao.generateTransferId(fileHashCode, fileName));
        }

        // 存在文件，则此文件不用再次上次，如果文件名称与上次上传文件名称不相同，则新增一条记录保存
        FileNameDTO newFileNameDTO = new FileNameDTO(
                UUID.randomUUID().toString(),
                fileInfoDTO.getHashCode(),
                fileName,
                LocalDateTime.now()
        );
        FileNameDTO fileNameDTO = fileMetaInfoDao.queryFileName(fileInfoDTO.getHashCode(),
                newFileNameDTO.getName(), newFileNameDTO.getExt());
        if(fileNameDTO != null) {
            return new FileStatusVO(FileStatus.EXIST, fileNameDTO.getFileId());
        }

        fileMetaInfoDao.saveFileName(newFileNameDTO);
        return new FileStatusVO(FileStatus.EXIST, newFileNameDTO.getFileId());
    }

    @Override
    public void uploadBlockFile(MultipartFile file, String transferId, int blockNum,
                                String blockHashCode, String fileHashCode) throws IOException, NoSuchAlgorithmException {
        //将文件保存到本地临时文件中
        File tempFile = new File(tempPath + File.separator + transferId + File.separator + blockNum);
        if(!tempFile.getParentFile().exists()){
            if(!tempFile.getParentFile().mkdirs()){
                throw new APIException("无法创建临时目录");
            }
        }
        file.transferTo(tempFile);

        //校验文件的哈希值
        String tempFileHashCode = FileUtil.calcSha256Hash(tempFile);
        if(!tempFileHashCode.equals(blockHashCode)) {
            throw new APIException("序号为%d的分块文件哈希值验证失败。传入哈希值：%s验证哈希值：%s"
                    .formatted(blockNum, blockHashCode, tempFileHashCode));
        }

        //将分块文件保存到存储介质中
        String storageId = fileStorageDao.saveBlockFile(transferId, fileHashCode, blockNum, tempFile);

        //保存分块文件信息记录
        BlockFileInfoDTO blockFileInfoDTO = new BlockFileInfoDTO(
                UUID.randomUUID().toString(),
                transferId, blockHashCode, blockNum,
                storageId, LocalDateTime.now()
        );
        fileMetaInfoDao.saveBlockFileInfo(blockFileInfoDTO);
    }

    @Override
    public String mergeFile(String transferId, String fileHashCode, String fileName) {
        //查询分块文件信息，合并分块文件保存到存储介质
        List<BlockFileInfoDTO> blockFileInfos = fileMetaInfoDao.getBlockFileInfosByTransferId(transferId);
        blockFileInfos.sort(Comparator.comparingInt(BlockFileInfoDTO::getNum));
        FileInfoDTO fileInfoDTO = fileStorageDao.saveMergeFile(transferId, fileHashCode, fileName, blockFileInfos);

        //合并成功后，保存文件信息
        fileMetaInfoDao.saveFileInfo(fileInfoDTO);

        //保存文件名称信息
        FileNameDTO fileNameDTO = new FileNameDTO(
                UUID.randomUUID().toString(),
                fileInfoDTO.getHashCode(),
                fileName, LocalDateTime.now());
        fileMetaInfoDao.saveFileName(fileNameDTO);

        //删除临时分块文件与信息
        deleteBlockFileAndInfo(transferId);

        //返回文件Id
        return fileNameDTO.getFileId();
    }

    @Override
    public void stopUpload(String transferId, String fileHashCode) {
        fileStorageDao.cleanBlockFile(transferId, fileHashCode);
        deleteBlockFileAndInfo(transferId);
    }

    @Override
    public void deleteFileByHashCode(List<String> fileHashCodes) {
        fileStorageDao.deleteFile(fileHashCodes);
        fileMetaInfoDao.deleteFileNameByHashCode(fileHashCodes);
        fileMetaInfoDao.deleteFileInfo(fileHashCodes);
    }

    /**
     * 删除临时文件夹中的分块文件，和元信息中记录的分块文件信息
     * @param transferId 文件传输Id
     */
    private void deleteBlockFileAndInfo(String transferId) {
        //如果配置不保留分块文件信息，则删除临时合并文件，以及合并文件信息
        if(!properties.isRetainBlocks()) {
            File tempFolder = new File(tempPath + File.separator + transferId);
            if (tempFolder.exists()) {
                for(File tempFile : Objects.requireNonNull(tempFolder.listFiles())) {
                    tempFile.delete();
                }
                tempFolder.delete();
            }
            fileMetaInfoDao.deleteBlockFileInfoByTransferId(transferId);
        }
    }

    @Override
    public void downloadFile(String fileId, HttpServletResponse response) throws IOException {
        //查询文件Id对应的文件信息
        FileFullInfoDTO fileFullInfoDTO = fileMetaInfoDao.getFileFullInfoByFileId(fileId);
        if(fileFullInfoDTO == null) {
            throw new APIException("未能找到" + fileId + "对应的文件");
        }

        //设置响应请求头，设置下载文件名称和大小
        response.reset();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileFullInfoDTO.getFileName() + "\"");
        response.setHeader("Content-Length", String.valueOf(fileFullInfoDTO.getFileInfo().getSize()));

        //下载文件，输出至文件流
        ServletOutputStream outputStream = response.getOutputStream();
        fileStorageDao.downloadFile(fileFullInfoDTO.getFileInfo().getStorageId(), outputStream);
        outputStream.close();
    }
}
