package org.fansuregrin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserQuery extends PageQuery {
    private Integer roleId;
    private String username;
    private String realname;
    private String email;
}
