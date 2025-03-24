package org.fansuregrin.controller;

import org.fansuregrin.entity.ApiResponse;
import org.fansuregrin.entity.Category;
import org.fansuregrin.service.CategoryService;
import org.fansuregrin.validation.ValidateGroup;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/category")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public ApiResponse getAll() {
        List<Category> data = categoryService.getAll();
        return ApiResponse.success(data);
    }

    @GetMapping("/id/{id}")
    public ApiResponse get(@PathVariable int id) {
        Category data = categoryService.get(id);
        return ApiResponse.success(data);
    }

    @GetMapping("/slug/{slug}")
    public ApiResponse getBySlug(@PathVariable String slug) {
        Category data = categoryService.getBySlug(slug);
        return ApiResponse.success(data);
    }

    @PostMapping
    public ApiResponse add(
        @RequestBody @Validated(ValidateGroup.Crud.Create.class) Category category) {
        categoryService.add(category);
        return ApiResponse.success("添加成功", null);
    }

    @PutMapping
    public ApiResponse update(
        @RequestBody @Validated(ValidateGroup.Crud.Update.class) Category category) {
        categoryService.update(category);
        return ApiResponse.success("更新成功", null);
    }

    @DeleteMapping
    public ApiResponse delete(@RequestBody List<Integer> ids) {
        categoryService.delete(ids);
        return ApiResponse.success("删除成功", null);
    }

}
