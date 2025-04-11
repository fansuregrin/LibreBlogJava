package org.fansuregrin.service;

import com.pig4cloud.captcha.SpecCaptcha;
import org.fansuregrin.constant.Constants;
import org.fansuregrin.entity.Captcha;
import org.fansuregrin.util.RedisUtil;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class CaptchaService {

    private final RedisUtil redisUtil;

    public CaptchaService(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    public Captcha generateCaptcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
        String verCode = specCaptcha.text().toLowerCase();
        String uuid = UUID.randomUUID().toString();
        redisUtil.set(Constants.CAPTCHA_REDIS_KEY_PREFIX + uuid, verCode, 3, TimeUnit.MINUTES);
        return new Captcha(specCaptcha.toBase64(), uuid);
    }

    public boolean verifyCaptcha(String uuid, String code) {
        String verCode = redisUtil.get(Constants.CAPTCHA_REDIS_KEY_PREFIX + uuid);
        return verCode != null && verCode.equals(code.toLowerCase());
    }

}
