package org.fansuregrin.service.impl;

import org.fansuregrin.annotation.PageCheck;
import org.fansuregrin.entity.Article;
import org.fansuregrin.entity.ArticleQuery;
import org.fansuregrin.entity.PageResult;
import org.fansuregrin.mapper.ArticleMapper;
import org.fansuregrin.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleMapper articleMapper;

    public ArticleServiceImpl(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    @PageCheck
    public PageResult<Article> list(ArticleQuery query) {
        int total = articleMapper.count(query);
        List<Article> articles = articleMapper.selectLimit(query);
        return new PageResult<>(total, articles);
    }

}
