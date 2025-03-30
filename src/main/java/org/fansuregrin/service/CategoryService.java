package org.fansuregrin.service;

import org.fansuregrin.entity.Category;
import org.fansuregrin.entity.CategoryQuery;
import org.fansuregrin.entity.PageResult;

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
