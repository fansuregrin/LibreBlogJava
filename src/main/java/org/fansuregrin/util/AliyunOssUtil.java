package org.fansuregrin.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
public class AliyunOssUtil {

    private final AliyunOssProperties aliyunOssProperties;

    public AliyunOssUtil(AliyunOssProperties aliyunOssProperties) {
        this.aliyunOssProperties = aliyunOssProperties;
    }

    public String upload(MultipartFile file, String directory) throws IOException {
        directory = directory == null ? "" : directory;
        String originalFilename = file.getOriginalFilename();
        String ext = originalFilename != null ?
            originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
        String filename = directory + "/" + UUID.randomUUID() + ext;

        InputStream inputStream = file.getInputStream();
        return upload(inputStream, filename);
    }

    public String upload(InputStream inputStream, String filename) {
        String endpoint = aliyunOssProperties.getEndpoint();
        String bucketName = aliyunOssProperties.getBucketName();
        String accessKeyId = aliyunOssProperties.getAccessKeyId();
        String accessKeySecret = aliyunOssProperties.getAccessKeySecret();

        OSS ossClient = new OSSClientBuilder()
            .build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, filename, inputStream);
        ossClient.shutdown();

        return endpoint.split("//")[0] + "//" + bucketName + "." +
            endpoint.split("//")[1] + "/" + filename;
    }
}
