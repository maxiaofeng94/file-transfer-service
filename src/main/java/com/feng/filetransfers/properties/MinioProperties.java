package com.feng.filetransfers.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "minio")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MinioProperties {
    /**
     * 服务端地址
     */
    private String endpoint;
    /**
     * 访问名
     */
    private String accessKey;
    /**
     * 访问密码
     */
    private String secretKey;
    /**
     * 桶名称
     */
    private String bucket;
}
