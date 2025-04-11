package org.fansuregrin.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.fansuregrin.constant.Constants;
import org.fansuregrin.entity.LoginInfo;
import org.fansuregrin.entity.User;
import org.fansuregrin.mapper.UserMapper;
import org.fansuregrin.service.TokenService;
import org.fansuregrin.util.RedisUtil;
import org.fansuregrin.util.UserUtil;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;
    private final RedisUtil redisUtil;
    private final UserMapper userMapper;

    public LoginInterceptor(TokenService tokenService, RedisUtil redisUtil, UserMapper userMapper) {
        this.tokenService = tokenService;
        this.redisUtil = redisUtil;
        this.userMapper = userMapper;
    }

    @Override
    public boolean preHandle(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response, @NonNull Object handler
    ) {
        LoginInfo loginInfo = tokenService.getLoginInfo(request);
        UserUtil.setLoginInfo(loginInfo);
        int uid = loginInfo.getUid();
        String key = Constants.USER_REDIS_KEY_PREFIX + uid;
        User loginUser = redisUtil.get(key);
        if (loginUser == null) {
            loginUser = userMapper.select(uid);
            redisUtil.set(key, loginUser);
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
        UserUtil.removeLoginInfo();
    }

}
