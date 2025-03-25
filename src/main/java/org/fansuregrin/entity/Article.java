package org.fansuregrin.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fansuregrin.validation.ValidateGroup;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class Article extends BaseEntity {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Null(groups = ValidateGroup.Crud.Create.class, message = "无需提供文章ID")
    @NotNull(groups = ValidateGroup.Crud.Update.class, message = "缺失id字段，请提供文章ID")
    private Integer id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer authorId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer categoryId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String excerpt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String content;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime createTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime modifyTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private User author;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Category category;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Tag> tags;
}
