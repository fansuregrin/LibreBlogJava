package org.fansuregrin.controller;

import org.fansuregrin.entity.ApiResponse;
import org.fansuregrin.entity.Article;
import org.fansuregrin.entity.ArticleQuery;
import org.fansuregrin.entity.PageResult;
import org.fansuregrin.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    public ApiResponse get(@PathVariable int id) {
        Article data = articleService.get(id);
        return ApiResponse.success(data);
    }

    @PostMapping
    public ApiResponse add(@RequestBody Article article) {
        articleService.add(article);
        return ApiResponse.success();
    }

    @PutMapping
    public ApiResponse update(@RequestBody Article article) {
        articleService.update(article);
        return ApiResponse.success();
    }

    @DeleteMapping
    public ApiResponse delete(@RequestBody List<Integer> ids) {
        articleService.delete(ids);
        return ApiResponse.success();
    }

}
