package org.fansuregrin;

import org.fansuregrin.util.AliyunOssProperties;
import org.fansuregrin.util.AliyunOssUtil;
import org.fansuregrin.util.UserUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

@SpringBootTest
class LibreBlogApplicationTests {

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private AliyunOssUtil aliyunOssUtil;

	@Test
	void testHashPassword() {
		String rawPassword = "Pw123#";
		String hashedPassword = UserUtil.hashPassword(rawPassword);
		System.out.println("raw password: " + rawPassword);
		System.out.println("hashed password: " + hashedPassword);
	}

	@Test
	void testVerifyPassword() {
		String rawPassword = "Pw123#";
		String hashedPassword = UserUtil.hashPassword(rawPassword);
		assert(UserUtil.verifyPassword(rawPassword, hashedPassword));
	}

	@Test
	void testReadCustomEnvVariables() {
		AliyunOssProperties aliyunOssProperties = appContext.getBean(
			AliyunOssProperties.class);
		String aliyunOssAccessKeyId = aliyunOssProperties.getAccessKeyId();
		String aliyunOssAccessKeySecret = aliyunOssProperties.getAccessKeySecret();
		System.out.println("aliyun oss access key id: " + aliyunOssAccessKeyId);
		System.out.println("aliyun oss access key secret: " + aliyunOssAccessKeySecret);
	}

	@Test
	void testUploadToAliyunOss() {
		URL url = this.getClass().getResource("test.txt");
        assert url != null;
		try (InputStream inputStream = new FileInputStream(new File(url.toURI()))) {
			aliyunOssUtil.upload(inputStream, "test.txt");
		} catch (Exception e) {
			System.out.println("upload failed");
			e.printStackTrace();
		}
	}

}
