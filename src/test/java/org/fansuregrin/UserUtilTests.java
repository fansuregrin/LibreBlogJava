package org.fansuregrin;


import org.fansuregrin.util.UserUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserUtilTests {

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

}
