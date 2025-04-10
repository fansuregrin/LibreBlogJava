package org.fansuregrin.dto;

import lombok.Data;

@Data
public class CategoryArticleCount {
    private Integer categoryId;
    private Long articleNum;
    private String categoryName;
    private String categorySlug;
}
