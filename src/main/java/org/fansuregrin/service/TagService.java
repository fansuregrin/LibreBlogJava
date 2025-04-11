package org.fansuregrin.service;

import org.fansuregrin.dto.PageResult;
import org.fansuregrin.entity.Tag;
import org.fansuregrin.dto.TagQuery;

import java.util.List;

public interface TagService {

    PageResult<Tag> listAdmin(TagQuery query);

    Tag get(int id);

    Tag getBySlug(String slug);

    void add(Tag tag);

    void update(Tag tag);

    void delete(List<Integer> ids);

}
