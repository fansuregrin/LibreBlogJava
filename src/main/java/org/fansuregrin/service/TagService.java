package org.fansuregrin.service;

import org.fansuregrin.entity.PageResult;
import org.fansuregrin.entity.Tag;
import org.fansuregrin.entity.TagQuery;

import java.util.List;

public interface TagService {

    List<Tag> getAll();

    PageResult<Tag> list(TagQuery query);

    Tag get(int id);

    Tag getBySlug(String slug);

    void add(Tag tag);

    void update(Tag tag);

    void delete(List<Integer> ids);

}
