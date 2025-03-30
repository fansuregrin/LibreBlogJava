package org.fansuregrin.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class Menu {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer parentId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String ancestors;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer sort;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Short type;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<Menu> subMenus;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Short scope;
}
