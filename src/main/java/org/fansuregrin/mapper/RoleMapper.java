package org.fansuregrin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.fansuregrin.entity.Role;
import org.fansuregrin.dto.RoleQuery;

import java.util.List;

@Mapper
public interface RoleMapper {

    Role select(Integer id);

    List<Role> selectLimit(RoleQuery query);

    long count(RoleQuery query);

}
