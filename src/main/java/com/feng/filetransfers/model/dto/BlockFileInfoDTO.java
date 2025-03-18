package com.feng.filetransfers.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 分块文件信息类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockFileInfoDTO {
    /**
     * 主键Id
     */
    private String id;

    /**
     * 文件传输Id
     * 用于记录分块文件对应传输的完整文件
     */
    private String transferId;

    /**
     * 分块文件的哈希值，当前哈希值使用文件的SHA256值记录
     */
    private String hashCode;

    /**
     * 分块文件序号
     */
    private String num;

    /**
     * 分块文件在存储介质中的id值
     */
    private String storageId;

    /**
     * 分块文件创建时间
     */
    private LocalDateTime createTime;
}
