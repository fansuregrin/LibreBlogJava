package org.fansuregrin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Captcha {

    private String captcha;

    private String uuid;

}
