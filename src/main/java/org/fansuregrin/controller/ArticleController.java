package org.fansuregrin.controller;

import org.fansuregrin.annotation.MenuPermissionCheck;
import org.fansuregrin.entity.*;
import org.fansuregrin.service.ArticleService;
import org.fansuregrin.validation.ValidateGroup;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles/list")
    public ApiResponse list(ArticleQuery query) {
        PageResult<Article> data = articleService.list(query);
        return ApiResponse.success(data);
    }

    @GetMapping("/articles/{id}")
    public ApiResponse get(@PathVariable int id) {
        Article data = articleService.get(id);
        return ApiResponse.success(data);
    }

    @GetMapping("/admin/articles/list")
    @MenuPermissionCheck("articleMgr:list")
    public ApiResponse listAdmin(ArticleQuery query) {
        PageResult<Article> data = articleService.listAdmin(query);
        return ApiResponse.success(data);
    }

    @PostMapping("/admin/articles")
    @MenuPermissionCheck("articleMgr:create")
    public ApiResponse add(
        @RequestBody @Validated(ValidateGroup.Crud.Create.class) Article article) {
        articleService.add(article);
        return ApiResponse.success();
    }

    @PutMapping("/admin/articles")
    @MenuPermissionCheck("articleMgr:update")
    public ApiResponse update(@RequestBody Article article) {
        articleService.update(article);
        return ApiResponse.success();
    }

    @DeleteMapping("/admin/articles")
    @MenuPermissionCheck("articleMgr:delete")
    public ApiResponse delete(@RequestBody List<Integer> ids) {
        articleService.delete(ids);
        return ApiResponse.success();
    }

}
