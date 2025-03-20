package com.feng.filetransfers.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file-transfer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileTransferProperties {
    /**
     * 文件传输组件在使用过程中存放临时文件的目录路径
     */
    private String tempPath = "temp";

    /**
     * 文件元信息存储数据源
     */
    private String metaInfoSource = "mongodb";

    /**
     * 文件存储介质数据源
     */
    private String storageSource = "minIO";

    /**
     * 是否保留分块文件及相关信息
     */
    private boolean retainBlocks = false;
}
