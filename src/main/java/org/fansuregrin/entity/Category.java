package org.fansuregrin.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.fansuregrin.validation.ValidateGroup;

import java.time.LocalDateTime;

@Data
public class Category {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Null(groups = ValidateGroup.Crud.Create.class, message = "无需提供分类ID")
    @NotNull(groups = ValidateGroup.Crud.Update.class, message = "缺失id字段，请提供分类ID")
    private Integer id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank(groups = ValidateGroup.Crud.Create.class, message = "分类名称不能为空")
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank(groups = ValidateGroup.Crud.Create.class, message = "分类缩略名不能为空")
    private String slug;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime createTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime modifyTime;
}
