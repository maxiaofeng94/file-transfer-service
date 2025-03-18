package com.feng.filetransfers.model.vo;

import com.feng.filetransfers.model.enums.FileStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件存在状态查询类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileStatusVO {
    /**
     * 文件存在状态
     */
    private FileStatus status;

    /**
     * 当文件存在时，此值表示文件Id
     * 当文件不存在时，此值表示文件传输Id
     */
    private String fileId;
}
