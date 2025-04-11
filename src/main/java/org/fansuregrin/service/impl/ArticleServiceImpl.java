package org.fansuregrin.service.impl;

import org.fansuregrin.annotation.PageCheck;
import org.fansuregrin.aop.PermissionAspect;
import org.fansuregrin.dto.ArticleQuery;
import org.fansuregrin.dto.CategoryArticleCount;
import org.fansuregrin.dto.PageResult;
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
        TagMapper tagMapper) {
        this.articleMapper = articleMapper;
        this.articleTagMapper = articleTagMapper;
        this.tagMapper = tagMapper;
    }

    @Override
    @PageCheck
    public PageResult<Article> list(ArticleQuery query) {
        long total = articleMapper.count(query);
        List<Article> articles = articleMapper.selectLimit(query);
        return new PageResult<>(total, articles);
    }

    @Override
    public Article get(int id) {
        return articleMapper.select(id);
    }

    @Override
    @PageCheck
    public PageResult<Article> listAdmin(ArticleQuery query) {
        Short scope = PermissionAspect.getScope();
        if (scope == null) {
            throw new PermissionException("没有权限");
        }
        if (RoleMenu.SCOPE_SELF.equals(scope)) {
            User loginUser = UserUtil.getLoginUser();
            query.setAuthorId(loginUser.getId());
        }
        long total = articleMapper.count(query);
        List<Article> articles = articleMapper.selectLimit(query);
        return new PageResult<>(total, articles);
    }

    @Override
    public List<CategoryArticleCount> articleCountPerCategory() {
        return articleMapper.groupByCategory();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void add(Article article) {
        Short scope = PermissionAspect.getScope();
        if (scope == null) {
            throw new PermissionException("没有权限");
        }
        if (RoleMenu.SCOPE_SELF.equals(scope)) {
            User loginUser = UserUtil.getLoginUser();
            if (!Objects.equals(article.getAuthorId(), loginUser.getId())) {
                throw new PermissionException("当前用户只能新增属于自己的文章");
            }
        }

        if (article.getCategoryId() == null) {
            article.setCategoryId(Category.DEFAULT_CATEGORY_ID);
        }
        articleMapper.insert(article);

        List<Tag> tags = article.getTags();
        if (tags != null) {
            for (Tag tag : tags) {
                String tagName = tag.getName();
                Tag tagInDb;
                if ((tagInDb = tagMapper.selectByNameForUpdate(tagName)) == null) {
                    if (tag.getSlug() == null) {
                        tag.setSlug(tagName);
                    }
                    tagMapper.insert(tag);
                } else {
                    tag = tagInDb;
                }
                articleTagMapper.insert(new ArticleTag(article.getId(), tag.getId()));
            }
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void update(Article article) {
        Short scope = PermissionAspect.getScope();
        if (RoleMenu.SCOPE_ALL.equals(scope)) {
            update0(article);
        } else if (RoleMenu.SCOPE_SELF.equals(scope)) {
            User loginUser = UserUtil.getLoginUser();
            Article oldArticle = articleMapper.selectForUpdate(article.getId());
            if (Objects.equals(oldArticle.getAuthorId(), loginUser.getId())) {
                update0(article);
            } else {
                throw new PermissionException("当前用户只能更新自己的文章");
            }
        } else {
            throw new PermissionException("没有权限");
        }
    }

    private void update0(Article article) {
        articleMapper.update(article); // 更新文章
        // 处理新的标签
        List<Tag> newTags = article.getTags();
        if (newTags == null) {
            return;
        }
        int articleId = article.getId();
        // 删除文章旧的标签
        articleTagMapper.deleteByArticles(List.of(articleId));
        for (Tag tag : newTags) {
            String tagName = tag.getName();
            Tag tagInDb;
            if ((tagInDb = tagMapper.selectByNameForUpdate(tagName)) == null) {
                if (tag.getSlug() == null) {
                    tag.setSlug(tagName);
                }
                tagMapper.insert(tag);
            } else {
                tag = tagInDb;
            }
            articleTagMapper.insert(new ArticleTag(articleId, tag.getId()));
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void delete(List<Integer> ids) {
        Short scope = PermissionAspect.getScope();
        if (RoleMenu.SCOPE_ALL.equals(scope)) {
            articleMapper.delete(ids, null);
            articleTagMapper.deleteByArticles(ids);
        } else if (RoleMenu.SCOPE_SELF.equals(scope)) {
            int uid = UserUtil.getLoginUser().getId();
            articleMapper.delete(ids, uid);
            articleTagMapper.deleteByUsersAndArticles(ids, List.of(uid));
        } else {
            throw new PermissionException("没有数据权限");
        }
    }

}
