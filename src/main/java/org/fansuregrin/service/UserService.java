package org.fansuregrin.service;

import org.fansuregrin.dto.PageResult;
import org.fansuregrin.entity.User;
import org.fansuregrin.dto.UserQuery;

import java.util.List;

public interface UserService {

    String login(User user);

    void register(User user);

    User getSelfGeneralInfo();

    User getGeneralInfo(int id);

    void updateSelfGeneralInfo(User user);

    void updateSelfPassword(User user);

    PageResult<User> list(UserQuery query);

    void add(User user);

    void updateGeneralInfo(User user);

    void updatePassword(User user);

    void delete(List<Integer> ids);

}
