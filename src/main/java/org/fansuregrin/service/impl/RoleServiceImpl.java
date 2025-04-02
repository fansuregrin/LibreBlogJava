package org.fansuregrin.service.impl;

import org.fansuregrin.annotation.PageCheck;
import org.fansuregrin.entity.PageResult;
import org.fansuregrin.entity.Role;
import org.fansuregrin.entity.RoleQuery;
import org.fansuregrin.mapper.RoleMapper;
import org.fansuregrin.service.RoleService;
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
        int total = roleMapper.count(query);
        List<Role> roles = roleMapper.selectLimit(query);
        return new PageResult<>(total, roles);
    }

}
