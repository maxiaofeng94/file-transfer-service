package com.feng.filetransfers.model.dos.mongodb;

import com.feng.filetransfers.model.dto.FileNameDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * 文件名称实体类
 * 与Mongodb库中的FileName集合对应
 */
@Document("FileName")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileNameDO {
    /**
     * 文件Id值
     */
    @Id
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

    public FileNameDO(FileNameDTO fileDTO) {
        this.fileId = fileDTO.getFileId();
        this.hashCode = fileDTO.getHashCode();
        this.name = fileDTO.getName();
        this.ext = fileDTO.getExt();
        this.createdTime = fileDTO.getCreatedTime();
    }

    /**
     * 转换为DTO对象
     * @return DTO对象
     */
    public FileNameDTO toDTO(){
        return new FileNameDTO(fileId, hashCode, name, ext, createdTime);
    }
}
