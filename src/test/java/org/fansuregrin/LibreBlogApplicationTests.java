package org.fansuregrin;

import org.fansuregrin.util.UserUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.util.UUID;

@SpringBootTest
class LibreBlogApplicationTests {

	@Autowired
	private ApplicationContext appContext;

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
		Assertions.assertTrue(UserUtil.verifyPassword(rawPassword, hashedPassword));
	}

	@Test
	void testRedisConnection() {
		RedisConnectionFactory redisConnectionFactory = appContext.getBean(
			RedisConnectionFactory.class);
		Assertions.assertNotNull(redisConnectionFactory);
		System.out.println(redisConnectionFactory.getClass().getName());

		RedisConnection connection = redisConnectionFactory.getConnection();
		byte[] k = ("test:" + UUID.randomUUID()).getBytes();
		byte[] v = "just for test".getBytes();
		connection.stringCommands().setEx(k, 10, v);
		byte[] vFromDb = connection.stringCommands().get(k);
		Assertions.assertArrayEquals(v, vFromDb);
	}

}
