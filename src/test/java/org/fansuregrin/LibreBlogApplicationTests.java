package org.fansuregrin;

import org.fansuregrin.util.UserUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class LibreBlogApplicationTests {

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

}
