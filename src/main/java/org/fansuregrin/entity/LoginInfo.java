package org.fansuregrin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfo {

    private Integer uid;

    private String uuid;

    private Long loginTime;

    private Long expireTime;

    private String ipAddress;

    private String browser;

}
