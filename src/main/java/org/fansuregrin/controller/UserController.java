package org.fansuregrin.controller;

import org.fansuregrin.annotation.MenuPermissionCheck;
import org.fansuregrin.entity.*;
import org.fansuregrin.service.CaptchaService;
import org.fansuregrin.service.TokenService;
import org.fansuregrin.service.UserService;
import org.fansuregrin.util.UserUtil;
import org.fansuregrin.validation.ValidateGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;
    private final CaptchaService captchaService;

    public UserController(UserService userService, TokenService tokenService, CaptchaService captchaService) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.captchaService = captchaService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
        @RequestBody @Validated(ValidateGroup.Crud.Query.Login.class) User user) {
        String token = userService.login(user);
        if (token != null) {
            return ResponseEntity.ok(ApiResponse.success("登录成功", token));
        } else {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error("用户名或密码错误"));
        }
    }

    @PostMapping("/register")
    public ApiResponse register(
        @RequestBody @Validated(ValidateGroup.Crud.Create.class) User user) {
        userService.register(user);
        return ApiResponse.success("注册成功", null);
    }

    @GetMapping("/logout")
    public ApiResponse logout() {
        tokenService.removeLoginInfo(UserUtil.getLoginInfo());
        return ApiResponse.success();
    }

    @GetMapping("/captcha")
    public ApiResponse getCaptcha() {
        Captcha captcha = captchaService.generateCaptcha();
        return ApiResponse.success(captcha);
    }

    @GetMapping("/users/{id}")
    public ApiResponse getGeneralInfo(@PathVariable int id) {
        User data = userService.getGeneralInfo(id);
        return ApiResponse.success(data);
    }

    @GetMapping("/admin/users/me")
    @MenuPermissionCheck("userMgr:get")
    public ApiResponse getGeneralInfo() {
        User user = userService.getSelfGeneralInfo();
        return ApiResponse.success(user);
    }

    @PatchMapping("/admin/users/me")
    @MenuPermissionCheck("userMgr:update")
    public ApiResponse updateSelfGeneralInfo(
        @RequestBody @Validated(ValidateGroup.Crud.Update.GeneralInfo.Self.class)
        User user
    ) {
        userService.updateSelfGeneralInfo(user);
        return ApiResponse.success();
    }

    @PatchMapping("/admin/users/me/password")
    @MenuPermissionCheck("userMgr:update")
    public ApiResponse updateSelfPassword(
        @RequestBody @Validated(ValidateGroup.Crud.Update.Password.Self.class)
        User user
    ) {
        userService.updateSelfPassword(user);
        return ApiResponse.success();
    }

    @GetMapping("/admin/users/list")
    @MenuPermissionCheck("userMgr:list")
    public ApiResponse list(UserQuery query) {
        PageResult<User> data = userService.list(query);
        return ApiResponse.success(data);
    }

    @PostMapping("/admin/users")
    @MenuPermissionCheck("userMgr:create")
    public ApiResponse add(
        @RequestBody @Validated(ValidateGroup.Crud.Create.class) User user) {
        userService.add(user);
        return ApiResponse.success("添加成功", null);
    }

    @PatchMapping("/admin/users")
    @MenuPermissionCheck("userMgr:update")
    public ApiResponse updateGeneralInfo(
        @RequestBody @Validated(ValidateGroup.Crud.Update.GeneralInfo.Other.class)
        User user
    ) {
        userService.updateGeneralInfo(user);
        return ApiResponse.success("更新成功", null);
    }

    @PatchMapping("/admin/users/password")
    @MenuPermissionCheck("userMgr:update")
    public ApiResponse updatePassword(
        @RequestBody @Validated(ValidateGroup.Crud.Update.Password.Other.class)
        User user
    ) {
        userService.updatePassword(user);
        return ApiResponse.success("更新成功", null);
    }

    @DeleteMapping("/admin/users")
    @MenuPermissionCheck("userMgr:delete")
    public ApiResponse delete(@RequestBody List<Integer> ids) {
        userService.delete(ids);
        return ApiResponse.success("删除成功", null);
    }

}
