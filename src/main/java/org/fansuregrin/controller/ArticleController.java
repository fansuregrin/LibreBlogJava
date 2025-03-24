package org.fansuregrin.controller;

import org.fansuregrin.entity.ApiResponse;
import org.fansuregrin.entity.Article;
import org.fansuregrin.entity.ArticleQuery;
import org.fansuregrin.entity.PageResult;
import org.fansuregrin.service.ArticleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/article")
@RestController
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/list")
    public ApiResponse list(ArticleQuery query) {
        PageResult<Article> data = articleService.list(query);
        return ApiResponse.success(data);
    }

}
