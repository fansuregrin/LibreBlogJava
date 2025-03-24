package org.fansuregrin.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class Role {
    public static int ADMINISTRATOR = 1;
    public static int EDITOR = 2;
    public static int CONTRIBUTOR = 3;
    public static int SUBSCRIBER = 4;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer menuId;
}
