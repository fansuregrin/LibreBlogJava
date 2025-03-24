package org.fansuregrin.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class ArticleTag {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer article;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer tag;
}
