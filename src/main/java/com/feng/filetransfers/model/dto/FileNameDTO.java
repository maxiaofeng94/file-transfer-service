package com.feng.filetransfers.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 文件名称类
 * 用于记录同一文件可能存在的不同名称
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileNameDTO {
    /**
     * 文件Id值
     */
    private String fileId;

    /**
     * 文件哈希值，当前哈希值使用文件的SHA256值记录
     */
    private String hashCode;

    /**
     * 文件名称，不包含后缀名
     */
    private String name;

    /**
     * 文件后缀名，不包含前缀“.”
     */
    private String ext;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;
}
