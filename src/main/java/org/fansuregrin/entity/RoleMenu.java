package org.fansuregrin.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenu {

    public static final Short SCOPE_ALL = 0;

    public static final Short SCOPE_SELF = 1;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer roleId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer menuId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Short scope;

}
