package org.fansuregrin.controller;

import org.fansuregrin.annotation.MenuPermissionCheck;
import org.fansuregrin.dto.ApiResponse;
import org.fansuregrin.entity.Menu;
import org.fansuregrin.service.MenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/admin/menus/me")
    @MenuPermissionCheck("menuMgr:list")
    public ApiResponse getSelfMenus(String code) {
        List<Menu> menus = menuService.getSelfMenus(code);
        return ApiResponse.success(menus);
    }

    @GetMapping("/admin/menus")
    @MenuPermissionCheck("menuMgr:list")
    public ApiResponse getMenus(String code, Integer userId) {
        List<Menu> menus = menuService.getMenus(code, userId);
        return ApiResponse.success(menus);
    }

}
