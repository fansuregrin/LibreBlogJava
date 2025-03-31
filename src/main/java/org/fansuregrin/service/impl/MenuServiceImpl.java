package org.fansuregrin.service.impl;

import org.fansuregrin.aop.PermissionAspect;
import org.fansuregrin.entity.Menu;
import org.fansuregrin.entity.RoleMenu;
import org.fansuregrin.entity.User;
import org.fansuregrin.exception.PermissionException;
import org.fansuregrin.mapper.MenuMapper;
import org.fansuregrin.service.MenuService;
import org.fansuregrin.util.UserUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;

    public MenuServiceImpl(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    @Override
    public List<Menu> getMenus(String code, Integer userId) {
        Short scope = PermissionAspect.getScope();
        if (RoleMenu.SCOPE_SELF.equals(scope)) {
            User loginUser = UserUtil.getLoginUser();
            if (!Objects.equals(loginUser.getId(), userId)) {
                throw new PermissionException("该用户只能查看自己的菜单");
            }
        }
        return menuMapper.selectIncludeSub(code, userId);
    }

    @Override
    public List<Menu> getSelfMenus(String code) {
        User loginUser = UserUtil.getLoginUser();
        return getMenus(code, loginUser.getId());
    }

}
