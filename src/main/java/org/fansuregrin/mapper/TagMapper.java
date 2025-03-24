package org.fansuregrin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.fansuregrin.entity.Tag;
import org.fansuregrin.entity.TagQuery;

import java.util.List;

@Mapper
public interface TagMapper {

    List<Tag> selectAll();

    Tag select(int id);

    Tag selectBySlug(String slug);

    List<Tag> selectLimit(TagQuery query);

    void insert(Tag tag);

    void update(Tag tag);

    void delete(List<Integer> ids);

    int count(TagQuery query);

}
