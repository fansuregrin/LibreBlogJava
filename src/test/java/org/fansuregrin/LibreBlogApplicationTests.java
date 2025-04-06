package org.fansuregrin;

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
