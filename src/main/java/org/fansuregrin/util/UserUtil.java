package org.fansuregrin.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.fansuregrin.entity.User;

public class UserUtil {
    private static final ThreadLocal<User> userHolder = new ThreadLocal<>();

    public static User getLoginUser() {
        return userHolder.get();
    }

    public static void setLoginUser(User user) {
        userHolder.set(user);
    }

    public static void removeUser() {
        userHolder.remove();
    }

    public static String hashPassword(String rawPassword) {
        String salt = DigestUtils.md5Hex(
            RandomStringUtils.secureStrong().nextAlphabetic(12))
            .toUpperCase();
        return hashWithSalt(rawPassword, salt);
    }

    private static String hashWithSalt(String s, String salt) {
        String h1 = DigestUtils.sha1Hex(s + salt).toUpperCase();
        String h2 = DigestUtils.sha256Hex(s + h1 + salt).toUpperCase();
        return h2 + salt;
    }

    public static boolean verifyPassword(String rawPassword, String hashedPassword) {
        String salt = hashedPassword.substring(64);
        return hashWithSalt(rawPassword, salt).equals(hashedPassword);
    }
}
