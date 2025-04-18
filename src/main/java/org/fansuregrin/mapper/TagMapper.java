package org.fansuregrin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.fansuregrin.entity.Tag;
import org.fansuregrin.dto.TagQuery;

import java.util.List;

@Mapper
public interface TagMapper {

    Tag select(int id);

    Tag selectBySlug(String slug);

    Tag selectByNameForUpdate(String name);

    Tag selectBySlugForUpdate(String slug);

    List<Tag> selectLimit(TagQuery query);

    void insert(Tag tag);

    void update(Tag tag);

    void delete(List<Integer> ids);

    long count(TagQuery query);

}
