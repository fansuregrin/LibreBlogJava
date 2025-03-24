package org.fansuregrin.service;

import org.fansuregrin.entity.Article;
import org.fansuregrin.entity.ArticleQuery;
import org.fansuregrin.entity.PageResult;

public interface ArticleService {

    PageResult<Article> list(ArticleQuery query);

}
