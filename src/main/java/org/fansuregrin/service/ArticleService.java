package org.fansuregrin.service;

import org.fansuregrin.entity.*;

import java.util.List;

public interface ArticleService {

    PageResult<Article> list(ArticleQuery query);

    Article get(int id);

    PageResult<Article> listAdmin(ArticleQuery query);

    void add(Article article);

    void update(Article article);

    void delete(List<Integer> ids);

}
