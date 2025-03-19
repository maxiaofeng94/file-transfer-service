package com.feng.filetransfers.model.dos.mongodb;

import com.feng.filetransfers.model.dto.BlockFileInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * 分块文件信息实体类
 * 与Mongodb库中的BlockFileInfo集合对应
 */
@Document("BlockFileInfo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockFileInfoDO {
    /**
     * 主键Id
     */
    @Id
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
    private int num;

    /**
     * 分块文件在存储介质中的id值
     */
    private String storageId;

    /**
     * 分块文件创建时间
     */
    private LocalDateTime createTime;

    public BlockFileInfoDO(BlockFileInfoDTO blockFileInfoDTO){
        this.id = blockFileInfoDTO.getId();
        this.transferId = blockFileInfoDTO.getTransferId();
        this.hashCode = blockFileInfoDTO.getHashCode();
        this.num = blockFileInfoDTO.getNum();
        this.storageId = blockFileInfoDTO.getStorageId();
        this.createTime = blockFileInfoDTO.getCreateTime();
    }

    /**
     * 转换为DTO对象
     * @return DTO对象
     */
    public BlockFileInfoDTO toDTO(){
        return new BlockFileInfoDTO(id, transferId, hashCode, num, storageId, createTime);
    }
}
