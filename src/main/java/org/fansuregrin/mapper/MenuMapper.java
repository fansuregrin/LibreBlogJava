package org.fansuregrin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.fansuregrin.entity.Menu;

@Mapper
public interface MenuMapper {

    Menu selectByCodeAndUserId(String code, int userId);

}
