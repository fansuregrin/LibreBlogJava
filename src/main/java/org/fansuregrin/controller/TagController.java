package org.fansuregrin.controller;

import org.fansuregrin.annotation.MenuPermissionCheck;
import org.fansuregrin.dto.ApiResponse;
import org.fansuregrin.dto.PageResult;
import org.fansuregrin.entity.Tag;
import org.fansuregrin.dto.TagQuery;
import org.fansuregrin.service.TagService;
import org.fansuregrin.validation.ValidateGroup;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tags/id/{id}")
    public ApiResponse get(@PathVariable int id) {
        Tag data = tagService.get(id);
        return ApiResponse.success(data);
    }

    @GetMapping("/tags/slug/{slug}")
    public ApiResponse getBySlug(@PathVariable String slug) {
        Tag data = tagService.getBySlug(slug);
        return ApiResponse.success(data);
    }

    @GetMapping("/admin/tags/list")
    @MenuPermissionCheck("tagMgr:list")
    public ApiResponse listAdmin(TagQuery query) {
        PageResult<Tag> data = tagService.listAdmin(query);
        return ApiResponse.success(data);
    }

    @PostMapping("/admin/tags")
    @MenuPermissionCheck("tagMgr:create")
    public ApiResponse add(
        @RequestBody @Validated(ValidateGroup.Crud.Create.class) Tag tag) {
        tagService.add(tag);
        return ApiResponse.success("添加成功", null);
    }

    @PutMapping("/admin/tags")
    @MenuPermissionCheck("tagMgr:update")
    public ApiResponse update(
        @RequestBody @Validated(ValidateGroup.Crud.Update.class) Tag tag) {
        tagService.update(tag);
        return ApiResponse.success("更新成功", null);
    }

    @DeleteMapping("/admin/tags")
    @MenuPermissionCheck("tagMgr:delete")
    public ApiResponse delete(@RequestBody List<Integer> ids) {
        tagService.delete(ids);
        return ApiResponse.success("删除成功", null);
    }

}
