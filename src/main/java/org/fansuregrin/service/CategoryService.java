package org.fansuregrin.service;

import org.fansuregrin.entity.Category;
import org.fansuregrin.dto.CategoryQuery;
import org.fansuregrin.dto.PageResult;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();

    PageResult<Category> listAdmin(CategoryQuery query);

    Category get(int id);

    Category getBySlug(String slug);

    void add(Category category);

    void update(Category category);

    void delete(List<Integer> ids);

}
