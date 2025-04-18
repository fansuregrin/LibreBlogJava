package org.fansuregrin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.fansuregrin.entity.User;
import org.fansuregrin.dto.UserQuery;

import java.util.List;

@Mapper
public interface UserMapper {

    User select(int id);

    User selectByUsername(String username);

    User selectForUpdate(int id);

    User selectByUsernameForUpdate(String username);

    void insert(User user);

    void update(User user);

    long count(UserQuery query);

    List<User> selectLimit(UserQuery query);

    void delete(List<Integer> ids);

}
