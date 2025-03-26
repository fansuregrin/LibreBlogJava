package org.fansuregrin.service.impl;

import org.fansuregrin.annotation.PageCheck;
import org.fansuregrin.entity.*;
import org.fansuregrin.exception.PermissionException;
import org.fansuregrin.mapper.ArticleMapper;
import org.fansuregrin.mapper.ArticleTagMapper;
import org.fansuregrin.mapper.TagMapper;
import org.fansuregrin.service.ArticleService;
import org.fansuregrin.util.UserUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleMapper articleMapper;
    private final ArticleTagMapper articleTagMapper;
    private final TagMapper tagMapper;

    public ArticleServiceImpl(
        ArticleMapper articleMapper, ArticleTagMapper articleTagMapper,
        TagMapper tagMapper
    ) {
        this.articleMapper = articleMapper;
        this.articleTagMapper = articleTagMapper;
        this.tagMapper = tagMapper;
    }

    @Override
    @PageCheck
    public PageResult<Article> list(ArticleQuery query) {
        int total = articleMapper.count(query);
        List<Article> articles = articleMapper.selectLimit(query);
        return new PageResult<>(total, articles);
    }

    @Override
    @PageCheck
    public PageResult<Article> selfList(ArticleQuery query) {
        User loginUser = UserUtil.getLoginUser();
        int roleId = loginUser.getRoleId();
        if (roleId == Role.SUBSCRIBER) {
            throw new PermissionException("没有权限");
        }
        if (roleId == Role.CONTRIBUTOR) {
            query.setAuthorId(loginUser.getId());
        }
        int total = articleMapper.count(query);
        List<Article> articles = articleMapper.selectLimit(query);
        return new PageResult<>(total, articles);
    }

    @Override
    public Article get(int id) {
        return articleMapper.select(id);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void add(Article article) {
        User loginUser = UserUtil.getLoginUser();
        int roleId = loginUser.getRoleId();
        if (Role.SUBSCRIBER == roleId) {
            throw new PermissionException("没有权限");
        }
        article.setAuthorId(loginUser.getId());
        if (article.getCategoryId() == null) {
            article.setCategoryId(Category.DEFAULT_CATEGORY_ID);
        }
        articleMapper.insert(article);
        List<Tag> tags = article.getTags();
        for (Tag tag : tags) {
            String tagName = tag.getName();
            if (tagMapper.selectByNameForUpdate(tagName) == null) {
                if (tag.getSlug() == null) {
                    tag.setSlug(tagName);
                }
                tagMapper.insert(tag);
            }
            articleTagMapper.insert(new ArticleTag(article.getId(), tag.getId()));
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void update(Article article) {
        User loginUser = UserUtil.getLoginUser();
        int roleId = loginUser.getRoleId();
        if (roleId == Role.ADMINISTRATOR || roleId == Role.EDITOR) {
            // 管理员和编辑可以更新任何文章
            update0(article);
        } else if (roleId == Role.CONTRIBUTOR) {
            // 贡献者只能更新自己的文章
            Article oldArticle = articleMapper.selectForUpdate(article.getId());
            if (Objects.equals(oldArticle.getAuthorId(), loginUser.getId())) {
                update0(article);
            } else {
                throw new PermissionException("没有权限");
            }
        } else {
            throw new PermissionException("没有权限");
        }
    }

    private void update0(Article article) {
        articleMapper.update(article); // 更新文章
        // 删除文章旧的标签
        articleTagMapper.deleteByArticles(List.of(article.getId()));
        // 处理新的标签
        List<Tag> newTags = article.getTags();
        for (Tag tag : newTags) {
            String tagName = tag.getName();
            if (tagMapper.selectByNameForUpdate(tagName) == null) {
                if (tag.getSlug() == null) {
                    tag.setSlug(tagName);
                }
                tagMapper.insert(tag);
            }
            articleTagMapper.insert(new ArticleTag(article.getId(), tag.getId()));
        }
    }

    @Override
    public void delete(List<Integer> ids) {
        User loginUser = UserUtil.getLoginUser();
        int roleId = loginUser.getRoleId();
        if (roleId == Role.ADMINISTRATOR || roleId == Role.EDITOR) {
            articleMapper.delete(ids, null);
        } else if (roleId == Role.CONTRIBUTOR) {
            articleMapper.delete(ids, loginUser.getId());
        } else {
            throw new PermissionException("没有权限");
        }
    }

}
