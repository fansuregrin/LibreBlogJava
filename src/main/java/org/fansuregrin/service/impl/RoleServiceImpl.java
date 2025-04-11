package org.fansuregrin.service.impl;

import org.fansuregrin.annotation.PageCheck;
import org.fansuregrin.aop.PermissionAspect;
import org.fansuregrin.dto.PageResult;
import org.fansuregrin.dto.RoleQuery;
import org.fansuregrin.entity.*;
import org.fansuregrin.mapper.RoleMapper;
import org.fansuregrin.service.RoleService;
import org.fansuregrin.util.UserUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    @PageCheck
    public PageResult<Role> listAdmin(RoleQuery query) {
        Short scope = PermissionAspect.getScope();
        if (RoleMenu.SCOPE_SELF.equals(scope)) {
            User loginUser = UserUtil.getLoginUser();
            query.setName(loginUser.getRole().getName());
        }
        long total = roleMapper.count(query);
        List<Role> roles = roleMapper.selectLimit(query);
        return new PageResult<>(total, roles);
    }

}
