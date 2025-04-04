package org.fansuregrin.controller;

import org.fansuregrin.annotation.MenuPermissionCheck;
import org.fansuregrin.entity.ApiResponse;
import org.fansuregrin.entity.Category;
import org.fansuregrin.entity.CategoryQuery;
import org.fansuregrin.entity.PageResult;
import org.fansuregrin.service.CategoryService;
import org.fansuregrin.validation.ValidateGroup;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories/all")
    public ApiResponse getAll() {
        List<Category> data = categoryService.getAll();
        return ApiResponse.success(data);
    }

    @GetMapping("/categories/id/{id}")
    public ApiResponse get(@PathVariable int id) {
        Category data = categoryService.get(id);
        return ApiResponse.success(data);
    }

    @GetMapping("/categories/slug/{slug}")
    public ApiResponse getBySlug(@PathVariable String slug) {
        Category data = categoryService.getBySlug(slug);
        return ApiResponse.success(data);
    }

    @GetMapping("/admin/categories/list")
    @MenuPermissionCheck("categoryMgr:list")
    public ApiResponse listAdmin(CategoryQuery query) {
        PageResult<Category> data = categoryService.listAdmin(query);
        return ApiResponse.success(data);
    }

    @PostMapping("/admin/categories")
    @MenuPermissionCheck("categoryMgr:create")
    public ApiResponse add(
        @RequestBody @Validated(ValidateGroup.Crud.Create.class) Category category) {
        categoryService.add(category);
        return ApiResponse.success("添加成功", null);
    }

    @PutMapping("/admin/categories")
    @MenuPermissionCheck("categoryMgr:update")
    public ApiResponse update(
        @RequestBody @Validated(ValidateGroup.Crud.Update.class) Category category) {
        categoryService.update(category);
        return ApiResponse.success("更新成功", null);
    }

    @DeleteMapping("/admin/categories")
    @MenuPermissionCheck("categoryMgr:delete")
    public ApiResponse delete(@RequestBody List<Integer> ids) {
        categoryService.delete(ids);
        return ApiResponse.success("删除成功", null);
    }

}
