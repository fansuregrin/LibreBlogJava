package org.fansuregrin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.fansuregrin.entity.ArticleTag;

import java.util.List;

@Mapper
public interface ArticleTagMapper {

    ArticleTag selectForUpdate(int articleId, int tagId);

    void insert(ArticleTag articleTag);

    void insertMulti(List<ArticleTag> articleTags);

    void deleteByArticles(List<Integer> ids);

    void deleteByTags(List<Integer> ids);

    void deleteByUsers(List<Integer> ids);

    void deleteByUsersAndArticles(List<Integer> articleIds, List<Integer> userIds);

}
