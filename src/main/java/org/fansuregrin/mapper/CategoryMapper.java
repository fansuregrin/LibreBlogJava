package org.fansuregrin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.fansuregrin.entity.Category;
import org.fansuregrin.entity.CategoryQuery;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<Category> selectAll();

    Category select(int id);

    int count(CategoryQuery query);

    List<Category> selectLimit(CategoryQuery query);

    Category selectForUpdate(int id);

    Category selectByNameForUpdate(String name);

    Category selectBySlugForUpdate(String slug);

    Category selectBySlug(String slug);

    void insert(Category category);

    void update(Category category);

    void delete(List<Integer> ids);

}
