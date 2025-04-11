package org.fansuregrin.controller;

import org.fansuregrin.annotation.MenuPermissionCheck;
import org.fansuregrin.dto.ApiResponse;
import org.fansuregrin.dto.PageResult;
import org.fansuregrin.entity.Role;
import org.fansuregrin.dto.RoleQuery;
import org.fansuregrin.service.RoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/admin/roles/list")
    @MenuPermissionCheck("roleMgr:list")
    public ApiResponse listAdmin(RoleQuery query) {
        PageResult<Role> data = roleService.listAdmin(query);
        return ApiResponse.success(data);
    }

}
