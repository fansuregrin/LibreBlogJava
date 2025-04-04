package org.fansuregrin.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOssProperties {

    private String endpoint;
    private String bucketName;
    private String accessKeyId;
    private String accessKeySecret;

}
