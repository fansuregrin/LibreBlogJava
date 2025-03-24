package org.fansuregrin.service.impl;

import org.fansuregrin.entity.Category;
import org.fansuregrin.entity.Role;
import org.fansuregrin.entity.User;
import org.fansuregrin.exception.DuplicateResourceException;
import org.fansuregrin.exception.PermissionException;
import org.fansuregrin.mapper.ArticleMapper;
import org.fansuregrin.mapper.CategoryMapper;
import org.fansuregrin.service.CategoryService;
import org.fansuregrin.util.UserUtil;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final ArticleMapper articleMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper, ArticleMapper articleMapper) {
        this.categoryMapper = categoryMapper;
        this.articleMapper = articleMapper;
    }

    @Override
    public List<Category> getAll() {
        return categoryMapper.selectAll();
    }

    @Override
    public Category get(int id) {
        return categoryMapper.select(id);
    }

    @Override
    public Category getBySlug(String slug) {
        return categoryMapper.selectBySlug(slug);
    }

    @Override
    public void update(Category category) {
        User loginUser = UserUtil.getLoginUser();
        int roleId = loginUser.getRoleId();
        if (Role.ADMINISTRATOR != roleId && Role.EDITOR != roleId) {
            throw new PermissionException("没有权限");
        }
        try {
            categoryMapper.update(category);
        } catch (DuplicateKeyException e) {
            throw new DuplicateResourceException("名称或缩略名被占用");
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void delete(List<Integer> ids) {
        User loginUser = UserUtil.getLoginUser();
        int roleId = loginUser.getRoleId();
        if (Role.ADMINISTRATOR != roleId && Role.EDITOR != roleId) {
            throw new PermissionException("没有权限");
        }
        articleMapper.resetCategoryId(ids);
        categoryMapper.delete(ids);
    }

    public void add(Category category) {
        User loginUser = UserUtil.getLoginUser();
        int roleId = loginUser.getRoleId();
        if (Role.ADMINISTRATOR != roleId && Role.EDITOR != roleId) {
            throw new PermissionException("没有权限");
        }
        try {
            categoryMapper.insert(category);
        } catch (DuplicateKeyException e) {
            throw new DuplicateResourceException("名称或缩略名被占用");
        }
    }

}
