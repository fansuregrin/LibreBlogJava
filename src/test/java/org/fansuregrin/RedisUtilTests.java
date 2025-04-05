package org.fansuregrin;

import org.fansuregrin.util.RedisUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisUtilTests {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    void testSet() {
        redisUtil.set("test", 12345);
        Assertions.assertEquals((Integer) 12345, redisUtil.get("test"));
    }

    @Test
    void testSetExpire() throws InterruptedException {
        redisUtil.set("test:expire", "just for test", 3, TimeUnit.SECONDS);
        Thread.sleep(4000);
        Assertions.assertNull(redisUtil.get("test:expire"));
    }

    @Test
    void testSetList() {
        List<String> value = new ArrayList<>();
        for (int i=0; i<10; ++i) {
            value.add("t" + i);
        }
        redisUtil.setList("test:list", value);
        List<String> valueFromDb = redisUtil.getList("test:list");
        Assertions.assertEquals(value, valueFromDb);
    }

    @Test
    void testSetSet() {
        Set<Integer> value = new HashSet<>();
        for (int i=0; i<10; ++i) {
            value.add(i);
        }
        redisUtil.setSet("test:set", value);
        Set<Integer> valueFromDb = redisUtil.getSet("test:set");
        Assertions.assertEquals(value, valueFromDb);
    }

    @Test
    void testSetMap() {
        Map<String, Integer> value = new HashMap<>();
        for (int i=0; i<5; ++i) {
            value.put("k" + i, i);
        }
        redisUtil.setMap("test:map", value);
        Map<String, Integer> valueFromDb = redisUtil.getMap("test:map");
        Assertions.assertEquals(value, valueFromDb);
    }

}
