package org.fansuregrin.controller;

import org.fansuregrin.dto.ApiResponse;
import org.fansuregrin.service.OssService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class OssController {

    private final OssService ossService;

    public OssController(OssService ossService) {
        this.ossService = ossService;
    }

    @PostMapping("/admin/upload")
    public ApiResponse upload(@RequestBody MultipartFile file) throws IOException {
        String url = ossService.upload(file);
        return ApiResponse.success(url);
    }

}
