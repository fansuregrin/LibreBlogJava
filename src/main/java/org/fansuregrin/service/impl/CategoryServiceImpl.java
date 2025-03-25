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
    @Transactional(rollbackFor = {Exception.class})
    public void update(Category category) {
        User loginUser = UserUtil.getLoginUser();
        int roleId = loginUser.getRoleId();
        if (Role.ADMINISTRATOR != roleId && Role.EDITOR != roleId) {
            throw new PermissionException("没有权限");
        }

        checkDuplicateName(category.getName());
        checkDuplicateSlug(category.getSlug());
        categoryMapper.update(category);
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

    @Transactional(rollbackFor = {Exception.class})
    public void add(Category category) {
        User loginUser = UserUtil.getLoginUser();
        int roleId = loginUser.getRoleId();
        if (Role.ADMINISTRATOR != roleId && Role.EDITOR != roleId) {
            throw new PermissionException("没有权限");
        }

        String categoryName = category.getName();
        if (categoryName != null) checkDuplicateName(categoryName);
        String categorySlug = category.getSlug();
        if (categorySlug != null) checkDuplicateSlug(categorySlug);
        categoryMapper.insert(category);
    }

    private void checkDuplicateName(String name) {
        if (categoryMapper.selectByNameForUpdate(name) != null) {
            throw new DuplicateResourceException("名称被占用：name = " + name);
        }
    }

    private void checkDuplicateSlug(String slug) {
        if (categoryMapper.selectBySlugForUpdate(slug) != null) {
            throw new DuplicateResourceException("缩略名被占用：slug = " + slug);
        }
    }

}
