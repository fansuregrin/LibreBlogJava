package org.fansuregrin.service.impl;

import org.fansuregrin.entity.User;
import org.fansuregrin.service.OssService;
import org.fansuregrin.util.AliyunOssUtil;
import org.fansuregrin.util.UserUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AliyunOssService implements OssService {

    private final AliyunOssUtil aliyunOssUtil;

    public AliyunOssService(AliyunOssUtil aliyunOssUtil) {
        this.aliyunOssUtil = aliyunOssUtil;
    }

    @Override
    public String upload(MultipartFile file) throws IOException {
        User loginUser = UserUtil.getLoginUser();
        String dir = loginUser.getUsername();
        return aliyunOssUtil.upload(file, dir);
    }

}
