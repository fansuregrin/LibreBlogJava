package org.fansuregrin.util;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unchecked")
@Component
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public <T> void set(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public <T> void set(
        final String key, final T value, final long timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public <T> T get(final String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    public boolean expire(
        final String key, final long timeout, final TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    public Long getExpire(final String key) {
        return redisTemplate.getExpire(key);
    }

    public Boolean hasKey(final String key) {
        return redisTemplate.hasKey(key);
    }

    public boolean delete(final String key) {
        return redisTemplate.delete(key);
    }

    public boolean delete(final Collection<String> keys) {
        return redisTemplate.delete(keys) > 0;
    }

    public <T> void setList(final String key, final List<T> data) {
        if (redisTemplate.hasKey(key)) {
            redisTemplate.delete(key);
        }
        redisTemplate.opsForList().rightPushAll(key, data.toArray());
    }

    public <T> List<T> getList(final String key) {
        return (List<T>) redisTemplate.opsForList().range(key, 0, -1);
    }

    public <T> void setSet(final String key, final Set<T> data) {
        if (redisTemplate.hasKey(key)) {
            redisTemplate.delete(key);
        }
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
        for (T value : data) {
            setOperations.add(key, value);
        }
    }

    public <T> Set<T> getSet(final String key) {
        return (Set<T>) redisTemplate.opsForSet().members(key);
    }

    public <T> void setMap(final String key, final Map<String, T> data) {
        if (data != null) {
            redisTemplate.opsForHash().putAll(key, data);
        }
    }

    public <T> Map<String, T> getMap(final String key) {
        HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
        return hashOperations.entries(key);
    }

    public <T> T getMapValue(final String key, final String hKey) {
        return (T) redisTemplate.opsForHash().get(key, hKey);
    }

    public <T> List<T> getMapValues(final String key, final Collection<Object> hKeys) {
        return (List<T>) redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    public boolean deleteMapValue(final String key, final String hKey) {
        return redisTemplate.opsForHash().delete(key, hKey) > 0;
    }

    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

}
