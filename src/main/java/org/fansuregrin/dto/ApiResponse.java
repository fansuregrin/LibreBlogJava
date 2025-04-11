package org.fansuregrin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private Integer code;
    private String msg;
    private Object data;

    public ApiResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ApiResponse success() {
        return new ApiResponse(Code.SUCCESS, "success", null);
    }

    public static ApiResponse success(Object data) {
        return new ApiResponse(Code.SUCCESS, "success", data);
    }

    public static ApiResponse success(String msg, Object data) {
        return new ApiResponse(Code.SUCCESS, msg, data);
    }

    public static ApiResponse warn(String msg) {
        return new ApiResponse(Code.WARN, msg, null);
    }

    public static ApiResponse error(String msg) {
        return new ApiResponse(Code.ERROR, msg, null);
    }

    static class Code {
        static int SUCCESS = 0;
        static int WARN = 1;
        static int ERROR = 2;
    }
}
