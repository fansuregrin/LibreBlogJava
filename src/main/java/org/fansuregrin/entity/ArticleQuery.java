package org.fansuregrin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleQuery extends PageQuery {
    private Integer authorId;
    private String categorySlug;
    private String tagSlug;
}
