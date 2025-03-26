package org.fansuregrin.service.impl;

import org.fansuregrin.annotation.PageCheck;
import org.fansuregrin.entity.*;
import org.fansuregrin.exception.DuplicateResourceException;
import org.fansuregrin.exception.PermissionException;
import org.fansuregrin.mapper.ArticleMapper;
import org.fansuregrin.mapper.CategoryMapper;
import org.fansuregrin.service.CategoryService;
import org.fansuregrin.util.UserUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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
    @PageCheck
    public PageResult<Category> selfList(CategoryQuery query) {
        User loginUser = UserUtil.getLoginUser();
        int roleId = loginUser.getRoleId();
        if (roleId != Role.ADMINISTRATOR && roleId != Role.EDITOR) {
            throw new PermissionException("没有权限");
        }
        int total = categoryMapper.count(query);
        List<Category> data = categoryMapper.selectLimit(query);
        return new PageResult<>(total, data);
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

        String categoryName = category.getName();
        Category oldCategory;
        if (categoryName != null &&
            (oldCategory = categoryMapper.selectByNameForUpdate(categoryName)) != null &&
            !Objects.equals(oldCategory.getId(), category.getId())) {
            throw new DuplicateResourceException("名称被占用：name = " + categoryName);
        }
        String categorySlug = category.getSlug();
        if (categorySlug != null &&
            (oldCategory = categoryMapper.selectBySlugForUpdate(categorySlug)) != null &&
            !Objects.equals(oldCategory.getId(), category.getId())) {
            throw new DuplicateResourceException("缩略名被占用：slug = " + categorySlug);
        }
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
        if (categoryMapper.selectByNameForUpdate(categoryName) != null) {
            throw new DuplicateResourceException("名称被占用：name = " + categoryName);
        }
        String categorySlug = category.getSlug();
        if (categoryMapper.selectBySlugForUpdate(categorySlug) != null) {
            throw new DuplicateResourceException("缩略名被占用：slug = " + categorySlug);
        }
        categoryMapper.insert(category);
    }

}
