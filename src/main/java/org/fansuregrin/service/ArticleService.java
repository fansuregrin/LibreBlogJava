package org.fansuregrin.service;

import org.fansuregrin.dto.CategoryArticleCount;
import org.fansuregrin.entity.*;

import java.util.List;

public interface ArticleService {

    PageResult<Article> list(ArticleQuery query);

    Article get(int id);

    PageResult<Article> listAdmin(ArticleQuery query);

    List<CategoryArticleCount> articleCountPerCategory();

    void add(Article article);

    void update(Article article);

    void delete(List<Integer> ids);

}
