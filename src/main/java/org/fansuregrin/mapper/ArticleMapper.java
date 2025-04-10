package org.fansuregrin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.fansuregrin.dto.CategoryArticleCount;
import org.fansuregrin.entity.Article;
import org.fansuregrin.entity.ArticleQuery;

import java.util.List;

@Mapper
public interface ArticleMapper {

    List<Article> selectLimit(ArticleQuery query);

    int count(ArticleQuery query);

    void resetCategoryId(List<Integer> categoryIds);

    void deleteByUsers(List<Integer> ids);

    Article select(int id);

    void delete(List<Integer> ids, Integer authorId);

    void update(Article article);

    void insert(Article article);

    Article selectForUpdate(int id);

    List<CategoryArticleCount> groupByCategory();

}
