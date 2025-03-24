package org.fansuregrin.service.impl;

import org.fansuregrin.annotation.PageCheck;
import org.fansuregrin.entity.*;
import org.fansuregrin.exception.DuplicateResourceException;
import org.fansuregrin.exception.PermissionException;
import org.fansuregrin.mapper.ArticleTagMapper;
import org.fansuregrin.mapper.TagMapper;
import org.fansuregrin.service.TagService;
import org.fansuregrin.util.UserUtil;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagMapper tagMapper;
    private final ArticleTagMapper articleTagMapper;

    public TagServiceImpl(TagMapper tagMapper, ArticleTagMapper articleTagMapper) {
        this.tagMapper = tagMapper;
        this.articleTagMapper = articleTagMapper;
    }

    @Override
    public List<Tag> getAll() {
        return tagMapper.selectAll();
    }

    @Override
    @PageCheck
    public PageResult<Tag> list(TagQuery query) {
        int total = tagMapper.count(query);
        List<Tag> tags = tagMapper.selectLimit(query);
        return new PageResult<>(total, tags);
    }

    @Override
    public Tag get(int id) {
        return tagMapper.select(id);
    }

    @Override
    public Tag getBySlug(String slug) {
        return tagMapper.selectBySlug(slug);
    }

    @Override
    public void add(Tag tag) {
        User loginUser = UserUtil.getLoginUser();
        int roleId = loginUser.getRoleId();
        if (Role.ADMINISTRATOR != roleId && Role.EDITOR != roleId) {
            throw new PermissionException("没有权限");
        }
        try {
            tagMapper.insert(tag);
        } catch (DuplicateKeyException e) {
            throw new DuplicateResourceException("名称或缩略名被占用");
        }
    }

    @Override
    public void update(Tag tag) {
        User loginUser = UserUtil.getLoginUser();
        int roleId = loginUser.getRoleId();
        if (Role.ADMINISTRATOR != roleId && Role.EDITOR != roleId) {
            throw new PermissionException("没有权限");
        }
        try {
            tagMapper.update(tag);
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
        articleTagMapper.deleteByTags(ids);
        tagMapper.delete(ids);
    }

}
