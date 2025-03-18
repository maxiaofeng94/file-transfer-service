package com.feng.filetransfers.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 文件信息类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfoDTO {
    /**
     * 文件哈希值，当前哈希值使用文件的SHA256值记录
     */
    private String hashCode;

    /**
     * 文件大小，单位为B
     */
    private long size;

    /**
     * 文件在存储介质中的Id值
     */
    private String storageId;

    /**
     * 文件创建时间
     */
    private LocalDateTime createdTime;
}
