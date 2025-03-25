package org.fansuregrin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.fansuregrin.entity.ArticleTag;

import java.util.List;

@Mapper
public interface ArticleTagMapper {

    void insert(ArticleTag articleTag);

    void insertMulti(List<ArticleTag> articleTags);

    void deleteByArticles(List<Integer> ids);

    void deleteByTags(List<Integer> ids);

    void deleteByUsers(List<Integer> ids);

}
