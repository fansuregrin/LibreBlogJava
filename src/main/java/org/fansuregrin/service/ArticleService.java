package org.fansuregrin.service;

import org.fansuregrin.entity.Article;
import org.fansuregrin.entity.ArticleQuery;
import org.fansuregrin.entity.PageResult;

import java.util.List;

public interface ArticleService {

    PageResult<Article> list(ArticleQuery query);

    Article get(int id);

    void add(Article article);

    void update(Article article);

    void delete(List<Integer> ids);

}
