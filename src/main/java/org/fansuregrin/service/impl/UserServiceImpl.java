package org.fansuregrin.service.impl;

import org.fansuregrin.annotation.PageCheck;
import org.fansuregrin.entity.PageResult;
import org.fansuregrin.entity.Role;
import org.fansuregrin.entity.User;
import org.fansuregrin.entity.UserQuery;
import org.fansuregrin.exception.LoginException;
import org.fansuregrin.exception.PermissionException;
import org.fansuregrin.exception.RequestDataException;
import org.fansuregrin.mapper.ArticleMapper;
import org.fansuregrin.mapper.ArticleTagMapper;
import org.fansuregrin.mapper.UserMapper;
import org.fansuregrin.service.UserService;
import org.fansuregrin.util.JwtUtil;
import org.fansuregrin.util.UserUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final JwtUtil jwtUtil;
    private final ArticleTagMapper articleTagMapper;
    private final ArticleMapper articleMapper;

    public UserServiceImpl(
        UserMapper userMapper, JwtUtil jwtUtil,
        ArticleTagMapper articleTagMapper, ArticleMapper articleMapper
    ) {
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
        this.articleTagMapper = articleTagMapper;
        this.articleMapper = articleMapper;
    }

    @Override
    public String login(User user) {
        String token = null;
        User userInDb = userMapper.selectByUsername(user.getUsername());
        if (userInDb != null &&
            UserUtil.verifyPassword(user.getPassword(), userInDb.getPassword())) {
            Map<String, Object> payload = new HashMap<>();
            payload.put("uid", userInDb.getId());
            token = jwtUtil.generateJwt(payload);
        }
        return token;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void register(User user) {
        user.setPassword(UserUtil.hashPassword(user.getPassword()));
        user.setCreateTime(LocalDateTime.now());
        user.setModifyTime(LocalDateTime.now());
        if (StringUtils.hasText(user.getRealname())) {
            user.setRealname(user.getUsername());
        }
        if (user.getRoleId() == null || user.getRoleId() <= 0) {
            user.setRoleId(Role.SUBSCRIBER);
        }

        // todo: check duplicate username
        userMapper.insert(user);
    }

    @Override
    public User getSelfGeneralInfo() {
        User loginUser = UserUtil.getLoginUser();
        if (loginUser == null) {
            throw new LoginException("登录失效，请重新登录");
        }
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
        user.setPassword(null);
        if (Role.ADMINISTRATOR != loginUser.getRoleId()) {
            user.setRole(null);
        }

        // todo: check duplicate username
        userMapper.update(user);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void updateSelfPassword(User user) {
        User loginUser = UserUtil.getLoginUser();
        int userId = loginUser.getId();
        user.setId(userId);
        User oldUser = userMapper.select(userId); // todo: select for update
        if (!UserUtil.verifyPassword(user.getOldPassword(), oldUser.getPassword())) {
            throw new RequestDataException("旧密码错误");
        }
        user.setPassword(UserUtil.hashPassword(user.getPassword()));
        userMapper.update(user);
    }

    @Override
    @PageCheck
    public PageResult<User> list(UserQuery query) {
        User loginUser = UserUtil.getLoginUser();
        if (Role.ADMINISTRATOR != loginUser.getRoleId()) {
            throw new PermissionException("没有权限");
        }

        int total = userMapper.count(query);
        List<User> users = userMapper.selectLimit(query);
        return new PageResult<>(total, users);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void add(User user) {
        User loginUser = UserUtil.getLoginUser();
        if (Role.ADMINISTRATOR != loginUser.getRoleId()) {
            throw new PermissionException("没有权限");
        }

        if (user.getRoleId() == null) {
            user.setRoleId(Role.SUBSCRIBER);
        }
        user.setPassword(UserUtil.hashPassword(user.getPassword()));
        // todo: check duplicate username
        userMapper.insert(user);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void updateGeneralInfo(User user) {
        User loginUser = UserUtil.getLoginUser();
        if (Role.ADMINISTRATOR != loginUser.getRoleId()) {
            throw new PermissionException("没有权限");
        }

        user.setPassword(null);
        // todo: check duplicate username
        userMapper.update(user);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void updatePassword(User user) {
        User loginUser = UserUtil.getLoginUser();
        if (Role.ADMINISTRATOR != loginUser.getRoleId()) {
            throw new PermissionException("没有权限");
        }

        User oldUser = userMapper.select(user.getId()); // todo: select for update
        if (!UserUtil.verifyPassword(user.getOldPassword(), oldUser.getPassword())) {
            throw new RequestDataException("旧密码错误");
        }
        user.setPassword(UserUtil.hashPassword(user.getPassword()));
        userMapper.update(user);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void delete(List<Integer> ids) {
        User loginUser = UserUtil.getLoginUser();
        if (Role.ADMINISTRATOR != loginUser.getRoleId()) {
            throw new PermissionException("没有权限");
        }
        articleMapper.deleteByUsers(ids);
        articleTagMapper.deleteByUsers(ids);
        userMapper.delete(ids);
    }

}
