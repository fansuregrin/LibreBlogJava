package org.fansuregrin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.fansuregrin.entity.Article;
import org.fansuregrin.entity.ArticleQuery;

import java.util.List;

@Mapper
public interface ArticleMapper {

    List<Article> selectLimit(ArticleQuery query);

    int count(ArticleQuery query);

    void resetCategoryId(List<Integer> categoryIds);

    void deleteByUsers(List<Integer> ids);

}
