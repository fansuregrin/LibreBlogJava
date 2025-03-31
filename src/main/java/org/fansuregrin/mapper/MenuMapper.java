package org.fansuregrin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.fansuregrin.entity.Menu;

import java.util.List;

@Mapper
public interface MenuMapper {

    Menu selectByCodeAndUserId(String code, int userId);

    List<Menu> selectIncludeSub(String code, Integer userId);

}
