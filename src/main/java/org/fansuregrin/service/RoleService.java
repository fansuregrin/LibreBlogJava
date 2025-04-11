package org.fansuregrin.service;

import org.fansuregrin.dto.PageResult;
import org.fansuregrin.entity.Role;
import org.fansuregrin.dto.RoleQuery;

public interface RoleService {

    PageResult<Role> listAdmin(RoleQuery query);

}
