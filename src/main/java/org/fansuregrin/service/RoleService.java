package org.fansuregrin.service;

import org.fansuregrin.entity.PageResult;
import org.fansuregrin.entity.Role;
import org.fansuregrin.entity.RoleQuery;

public interface RoleService {

    PageResult<Role> listAdmin(RoleQuery query);

}
