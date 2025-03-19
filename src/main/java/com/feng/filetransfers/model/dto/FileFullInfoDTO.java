package com.feng.filetransfers.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件完整信息类
 * 包含FileInfoDTO对象和FileNameDTO对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileFullInfoDTO {
    /**
     * 文件信息
     */
    private FileInfoDTO fileInfo;

    /**
     * 文件名称
     */
    private FileNameDTO fileName;

    /**
     * 获取文件完整名称，包含后缀
     * @return 文件完整名称
     */
    public String getFileName(){
        return fileName.getName() + "." + fileName.getExt();
    }
}
