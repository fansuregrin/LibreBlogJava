package org.fansuregrin.service.impl;

import org.fansuregrin.annotation.PageCheck;
import org.fansuregrin.dto.PageResult;
import org.fansuregrin.dto.TagQuery;
import org.fansuregrin.entity.*;
import org.fansuregrin.exception.DuplicateResourceException;
import org.fansuregrin.mapper.ArticleTagMapper;
import org.fansuregrin.mapper.TagMapper;
import org.fansuregrin.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class TagServiceImpl implements TagService {

    private final TagMapper tagMapper;
    private final ArticleTagMapper articleTagMapper;

    public TagServiceImpl(TagMapper tagMapper, ArticleTagMapper articleTagMapper) {
        this.tagMapper = tagMapper;
        this.articleTagMapper = articleTagMapper;
    }

    @Override
    @PageCheck
    public PageResult<Tag> listAdmin(TagQuery query) {
        long total = tagMapper.count(query);
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
    @Transactional(rollbackFor = {Exception.class})
    public void add(Tag tag) {
        String tagName = tag.getName();
        if (tagMapper.selectByNameForUpdate(tagName) != null) {
            throw new DuplicateResourceException("名称被占用：name = " + tagName);
        }
        String tagSlug = tag.getSlug();
        if (tagMapper.selectBySlugForUpdate(tagSlug) != null) {
            throw new DuplicateResourceException("缩略名被占用：slug = " + tagSlug);
        }

        tagMapper.insert(tag);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void update(Tag tag) {
        String tagName = tag.getName();
        Tag oldTag;
        if (tagName != null &&
            (oldTag = tagMapper.selectByNameForUpdate(tagName)) != null &&
            !Objects.equals(oldTag.getId(), tag.getId())) {
            throw new DuplicateResourceException("名称被占用：name = " + tagName);
        }
        String tagSlug = tag.getSlug();
        if (tagSlug != null &&
            (oldTag = tagMapper.selectBySlugForUpdate(tagSlug)) != null &&
            !Objects.equals(oldTag.getId(), tag.getId())) {
            throw new DuplicateResourceException("缩略名被占用：slug = " + tagSlug);
        }

        tagMapper.update(tag);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void delete(List<Integer> ids) {
        articleTagMapper.deleteByTags(ids);
        tagMapper.delete(ids);
    }

}
