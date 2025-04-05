package org.fansuregrin;

import org.fansuregrin.util.AliyunOssProperties;
import org.fansuregrin.util.AliyunOssUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

@SpringBootTest
public class AliyunOssUtilTests {

    @Autowired
    private AliyunOssProperties aliyunOssProperties;

    @Autowired
    private AliyunOssUtil aliyunOssUtil;

    @Test
    void testReadPropertiesFromEnvVariables() {
        String aliyunOssAccessKeyId = aliyunOssProperties.getAccessKeyId();
        String aliyunOssAccessKeySecret = aliyunOssProperties.getAccessKeySecret();
        System.out.println("aliyun oss access key id: " + aliyunOssAccessKeyId);
        System.out.println("aliyun oss access key secret: " + aliyunOssAccessKeySecret);
    }

    @Test
    void testUploadToAliyunOss() {
        URL url = this.getClass().getResource("test.txt");
        Assertions.assertNotNull(url);
        try (InputStream inputStream = new FileInputStream(new File(url.toURI()))) {
            aliyunOssUtil.upload(inputStream, "test.txt");
        } catch (Exception e) {
            System.out.println("upload failed");
            e.printStackTrace();
        }
    }

}
