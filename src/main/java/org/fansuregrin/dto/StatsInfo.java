package org.fansuregrin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class StatsInfo {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long articleCount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long userCount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long categoryCount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long tagCount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long onlineUserCount;

}
