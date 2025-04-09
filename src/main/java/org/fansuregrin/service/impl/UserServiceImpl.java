package org.fansuregrin.service.impl;

import org.fansuregrin.annotation.PageCheck;
import org.fansuregrin.aop.PermissionAspect;
import org.fansuregrin.entity.*;
import org.fansuregrin.exception.DuplicateResourceException;
import org.fansuregrin.exception.LoginException;
import org.fansuregrin.exception.PermissionException;
import org.fansuregrin.exception.RequestDataException;
import org.fansuregrin.mapper.ArticleMapper;
import org.fansuregrin.mapper.ArticleTagMapper;
import org.fansuregrin.mapper.UserMapper;
import org.fansuregrin.service.CaptchaService;
import org.fansuregrin.service.TokenService;
import org.fansuregrin.service.UserService;
import org.fansuregrin.util.RedisUtil;
import org.fansuregrin.util.UserUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final ArticleTagMapper articleTagMapper;
    private final ArticleMapper articleMapper;
    private final TokenService tokenService;
    private final RedisUtil redisUtil;
    private final CaptchaService captchaService;

    public UserServiceImpl(
        UserMapper userMapper, ArticleTagMapper articleTagMapper,
        ArticleMapper articleMapper, TokenService tokenService, RedisUtil redisUtil, CaptchaService captchaService) {
        this.userMapper = userMapper;
        this.articleTagMapper = articleTagMapper;
        this.articleMapper = articleMapper;
        this.tokenService = tokenService;
        this.redisUtil = redisUtil;
        this.captchaService = captchaService;
    }

    @Override
    public String login(User user) {
        if (!captchaService.verifyCaptcha(user.getUuid(), user.getVerifyCode())) {
            throw new RequestDataException("验证码错误");
        }
        String token = null;
        User userInDb = userMapper.selectByUsername(user.getUsername());
        if (userInDb != null &&
            UserUtil.verifyPassword(user.getPassword(), userInDb.getPassword())) {
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setUid(userInDb.getId());
            token = tokenService.createToken(loginInfo);
        }
        return token;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void register(User user) {
        user.setPassword(UserUtil.hashPassword(user.getPassword()));
        user.setCreateTime(LocalDateTime.now());
        user.setModifyTime(LocalDateTime.now());
        if (!StringUtils.hasText(user.getRealname())) {
            user.setRealname(user.getUsername());
        }
        if (user.getRoleId() == null || user.getRoleId() <= 0) {
            user.setRoleId(Role.SUBSCRIBER);
        }

        String username = user.getUsername();
        if (userMapper.selectByUsernameForUpdate(username) != null) {
            throw new DuplicateResourceException("用户名被占用：username = " + username);
        }
        userMapper.insert(user);
    }

    @Override
    public User getSelfGeneralInfo() {
        User loginUser = UserUtil.getLoginUser();
        int uid = loginUser.getId();
        return getGeneralInfo(uid);
    }

    @Override
    public User getGeneralInfo(int id) {
        User user = userMapper.select(id);
        user.setPassword(null);
        return user;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void updateSelfGeneralInfo(User user) {
        User loginUser = UserUtil.getLoginUser();
        user.setId(loginUser.getId());
        updateGeneralInfo(user);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void updateGeneralInfo(User user) {
        Short scope = PermissionAspect.getScope();
        User loginUser = UserUtil.getLoginUser();
        if (RoleMenu.SCOPE_SELF.equals(scope) &&
            !Objects.equals(user.getId(), loginUser.getId())) {
            throw new PermissionException("该用户只能更新自己的信息");
        }
        // 检查更新的用户是否存在
        User oldUser = userMapper.selectForUpdate(user.getId());
        if (oldUser == null) {
            throw new RequestDataException("用户不存在");
        }
        // 检查新的用户名是否被占用
        String username = user.getUsername();
        if (username != null) {
            User userInDb = userMapper.selectByUsernameForUpdate(username);
            if (userInDb != null && !Objects.equals(userInDb.getId(), user.getId())) {
                throw new DuplicateResourceException("用户名被占用：username = " + username);
            }
        }
        // 非管理员无法更新用户的角色
        Integer newRoleId = user.getRoleId();
        if (newRoleId != null) {
            if (loginUser.getRoleId() != Role.ADMINISTRATOR &&
                !newRoleId.equals(oldUser.getRoleId())) {
                throw new PermissionException("没有权限更新用户的角色");
            }
        }
        user.setPassword(null);
        userMapper.update(user);
        redisUtil.delete("user:" + user.getId());
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void updateSelfPassword(User user) {
        User loginUser = UserUtil.getLoginUser();
        user.setId(loginUser.getId());
        updatePassword(user);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void updatePassword(User user) {
        Short scope = PermissionAspect.getScope();
        User loginUser = UserUtil.getLoginUser();
        if (RoleMenu.SCOPE_SELF.equals(scope) &&
            !Objects.equals(user.getId(), loginUser.getId())) {
            throw new PermissionException("该用户只能更新自己的密码");
        }
        User oldUser = userMapper.selectForUpdate(user.getId());
        if (!UserUtil.verifyPassword(user.getOldPassword(), oldUser.getPassword())) {
            throw new RequestDataException("旧密码错误");
        }
        user.setPassword(UserUtil.hashPassword(user.getPassword()));
        userMapper.update(user);
        tokenService.removeLoginInfo(user.getId());
    }

    @Override
    @PageCheck
    public PageResult<User> list(UserQuery query) {
        Short scope = PermissionAspect.getScope();
        if (scope == null) {
            throw new PermissionException("没有权限");
        }
        if (RoleMenu.SCOPE_SELF.equals(scope)) {
            User loginUser = UserUtil.getLoginUser();
            query.setUsername(loginUser.getUsername());
        }

        int total = userMapper.count(query);
        List<User> users = userMapper.selectLimit(query);
        for (User user : users) {
            user.setPassword(null);
        }
        return new PageResult<>(total, users);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void add(User user) {
        if (user.getRoleId() == null) {
            user.setRoleId(Role.SUBSCRIBER);
        }
        user.setPassword(UserUtil.hashPassword(user.getPassword()));

        String username = user.getUsername();
        if (userMapper.selectByUsernameForUpdate(username) != null) {
            throw new DuplicateResourceException("用户名被占用：username = " + username);
        }
        userMapper.insert(user);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void delete(List<Integer> ids) {
        articleMapper.deleteByUsers(ids);
        articleTagMapper.deleteByUsers(ids);
        userMapper.delete(ids);
        ids.forEach(tokenService::removeLoginInfo);
    }

}
