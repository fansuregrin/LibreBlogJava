package org.fansuregrin.interceptor;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.fansuregrin.entity.User;
import org.fansuregrin.exception.LoginException;
import org.fansuregrin.mapper.UserMapper;
import org.fansuregrin.util.JwtUtil;
import org.fansuregrin.util.UserUtil;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;

    public LoginInterceptor(JwtUtil jwtUtil, UserMapper userMapper) {
        this.jwtUtil = jwtUtil;
        this.userMapper = userMapper;
    }

    @Override
    public boolean preHandle(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response, @NonNull Object handler
    ) {
        String header = request.getHeader("Authorization");
        String token;
        if (header == null || !header.startsWith("Bearer ")) {
            throw new LoginException("请先登录");
        }
        token = header.substring(7);
        Claims claims;
        try {
            claims = jwtUtil.parseJwt(token);
        } catch (JwtException e) {
            throw new LoginException("登录失效，请重新登录", e);
        }
        Integer uid = (Integer) claims.get("uid");
        User loginUser = userMapper.select(uid);
        if (loginUser == null) {
            throw new LoginException("登录失效，请重新登录");
        }
        UserUtil.setLoginUser(loginUser);
        return true;
    }

    @Override
    public void afterCompletion(
        @NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
        @NonNull Object handler, Exception ex
    ) {
        UserUtil.removeUser();
    }

}
