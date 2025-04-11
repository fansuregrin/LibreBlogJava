package org.fansuregrin.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.fansuregrin.constant.Constants;
import org.fansuregrin.entity.LoginInfo;
import org.fansuregrin.exception.LoginException;
import org.fansuregrin.util.JwtUtil;
import org.fansuregrin.util.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final long TOKEN_REFRESH_GAP = 600000;

    private final RedisUtil redisUtil;

    private final JwtUtil jwtUtil;

    @Value("${libreblog.token.expire-time}")
    private long expireTime;

    @Value("${libreblog.token.header}")
    private String headerName;

    public TokenService(RedisUtil redisUtil, JwtUtil jwtUtil) {
        this.redisUtil = redisUtil;
        this.jwtUtil = jwtUtil;
    }

    public void removeLoginInfo(LoginInfo loginInfo) {
        redisUtil.delete(getKey(loginInfo));
    }

    public void removeLoginInfo(int uid) {
        redisUtil.deleteWithPrefix(Constants.LOGIN_INFO_REDIS_KEY_PREFIX + uid + ":");
    }

    public String createToken(LoginInfo loginInfo) {
        String uuid = UUID.randomUUID().toString();
        loginInfo.setUuid(uuid);

        refreshToken(loginInfo);

        Map<String, Object> claims = new HashMap<>();
        claims.put("uid", loginInfo.getUid());
        claims.put("uuid", uuid);
        return jwtUtil.generateJwt(claims);
    }

    public LoginInfo getLoginInfo(HttpServletRequest request) {
        String token = getToken(request);
        if (!StringUtils.hasLength(token)) {
            return null;
        }

        Claims claims;
        try {
            claims = jwtUtil.parseJwt(token);
        } catch (JwtException e) {
            throw new LoginException("登录失效，请重新登录", e);
        }
        int uid = (Integer) claims.get("uid");
        String uuid = (String) claims.get("uuid");
        LoginInfo loginInfo = redisUtil.get(getKey(uid, uuid));
        if (loginInfo == null) {
            throw new LoginException("登录失效，请重新登录");
        }

        if (loginInfo.getExpireTime() - System.currentTimeMillis() <= TOKEN_REFRESH_GAP) {
            refreshToken(loginInfo);
        }

        return loginInfo;
    }

    private void refreshToken(LoginInfo loginInfo) {
        long currentTime = System.currentTimeMillis();
        loginInfo.setLoginTime(currentTime);
        loginInfo.setExpireTime(currentTime + expireTime);
        redisUtil.set(getKey(loginInfo), loginInfo, expireTime, TimeUnit.MILLISECONDS);
    }

    private String getToken(HttpServletRequest request) {
        String header = request.getHeader(headerName);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            throw new LoginException("请先登录");
        }
        return header.substring(TOKEN_PREFIX.length());
    }

    private String getKey(LoginInfo loginInfo) {
        return getKey(loginInfo.getUid(), loginInfo.getUuid());
    }

    private String getKey(int uid, String uuid) {
        return Constants.LOGIN_INFO_REDIS_KEY_PREFIX + uid  + ":" + uuid;
    }

}
