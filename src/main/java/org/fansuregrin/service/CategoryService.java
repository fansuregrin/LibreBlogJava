package org.fansuregrin.service;

import org.fansuregrin.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();

    Category get(int id);

    Category getBySlug(String slug);

    void add(Category category);

    void update(Category category);

    void delete(List<Integer> ids);

}
