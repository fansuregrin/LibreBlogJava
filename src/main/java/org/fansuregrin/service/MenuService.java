package org.fansuregrin.service;

import org.fansuregrin.entity.Menu;

import java.util.List;

public interface MenuService {

    List<Menu> getMenus(String code, Integer userId);

    List<Menu> getSelfMenus(String code);
}
