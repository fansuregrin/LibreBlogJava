package org.fansuregrin.controller;

import org.fansuregrin.entity.ApiResponse;
import org.fansuregrin.entity.PageResult;
import org.fansuregrin.entity.Tag;
import org.fansuregrin.entity.TagQuery;
import org.fansuregrin.service.TagService;
import org.fansuregrin.validation.ValidateGroup;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/tag")
@RestController
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/all")
    public ApiResponse getAll() {
        List<Tag> data = tagService.getAll();
        return ApiResponse.success(data);
    }

    @GetMapping("list")
    public ApiResponse list(TagQuery query) {
        PageResult<Tag> data = tagService.list(query);
        return ApiResponse.success(data);
    }

    @GetMapping("/id/{id}")
    public ApiResponse get(@PathVariable int id) {
        Tag data = tagService.get(id);
        return ApiResponse.success(data);
    }

    @GetMapping("/slug/{slug}")
    public ApiResponse getBySlug(@PathVariable String slug) {
        Tag data = tagService.getBySlug(slug);
        return ApiResponse.success(data);
    }

    @PostMapping
    public ApiResponse add(
        @RequestBody @Validated(ValidateGroup.Crud.Create.class) Tag tag) {
        tagService.add(tag);
        return ApiResponse.success("添加成功", null);
    }

    @PutMapping
    public ApiResponse update(
        @RequestBody @Validated(ValidateGroup.Crud.Update.class) Tag tag) {
        tagService.update(tag);
        return ApiResponse.success("更新成功", null);
    }

    @DeleteMapping
    public ApiResponse delete(@RequestBody List<Integer> ids) {
        tagService.delete(ids);
        return ApiResponse.success("删除成功", null);
    }

}
