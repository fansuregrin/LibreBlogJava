package org.fansuregrin.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleTag {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer article;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer tag;
}
