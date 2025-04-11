package org.fansuregrin.service.impl;

import org.fansuregrin.constant.Constants;
import org.fansuregrin.dto.StatsInfo;
import org.fansuregrin.mapper.ArticleMapper;
import org.fansuregrin.mapper.CategoryMapper;
import org.fansuregrin.mapper.TagMapper;
import org.fansuregrin.mapper.UserMapper;
import org.fansuregrin.util.RedisUtil;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final ArticleMapper articleMapper;
    private final UserMapper userMapper;
    private final CategoryMapper categoryMapper;
    private final TagMapper tagMapper;
    private final RedisUtil redisUtil;

    public DashboardService(ArticleMapper articleMapper, UserMapper userMapper, CategoryMapper categoryMapper, TagMapper tagMapper, RedisUtil redisUtil) {
        this.articleMapper = articleMapper;
        this.userMapper = userMapper;
        this.categoryMapper = categoryMapper;
        this.tagMapper = tagMapper;
        this.redisUtil = redisUtil;
    }

    public StatsInfo stats() {
        StatsInfo info = new StatsInfo();
        info.setArticleCount(articleMapper.count(null));
        info.setUserCount(userMapper.count(null));
        info.setCategoryCount(categoryMapper.count(null));
        info.setTagCount(tagMapper.count(null));
        info.setOnlineUserCount(redisUtil.count(Constants.LOGIN_INFO_REDIS_KEY_PREFIX));
        return info;
    }

}
