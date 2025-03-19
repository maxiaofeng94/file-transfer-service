package com.feng.filetransfers.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "filetransfer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileTransferProperties {
    /**
     * 文件传输组件在使用过程中存放临时文件的目录路径
     */
    private String tempPath = "temp";
}
