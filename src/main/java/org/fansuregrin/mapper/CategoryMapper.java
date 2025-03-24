package org.fansuregrin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.fansuregrin.entity.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<Category> selectAll();

    Category select(int id);

    Category selectBySlug(String slug);

    void insert(Category category);

    void update(Category category);

    void delete(List<Integer> ids);

}
