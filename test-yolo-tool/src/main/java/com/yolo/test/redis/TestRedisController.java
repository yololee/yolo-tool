package com.yolo.test.redis;

import com.yolo.common.api.R;
import com.yolo.common.support.utils.jackson.JsonUtil;
import com.yolo.redis.anno.RateLimiter;
import com.yolo.redis.anno.RepeatSubmit;
import com.yolo.redis.utils.RedisUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
public class TestRedisController {


    private final RedisTemplate<String,Object> redisTemplate;

    private final RedisUtil redisUtil;

    @GetMapping("/set")
    public R<String> set(){
        User user = new User();
        user.setName("zhangsan");
        user.setAge(24);
        redisTemplate.opsForValue().set("yolo", Objects.requireNonNull(JsonUtil.toJson(user)));
        return R.success("OK");
    }

    @GetMapping("/get")
    @RateLimiter(value = "1",max = 2,ttl = 5,timeUnit = TimeUnit.SECONDS)
    public R<User> get(){
        User yolo = JsonUtil.readValue(Objects.requireNonNull(redisUtil.get("yolo")).toString(), User.class);
        return R.success(yolo);
    }

    @GetMapping("/test")
    @Cacheable(cacheNames = "redis",key = "#name")
    public R<User> test(String name){
        System.out.println("从数据库中进行查询");
        User user = new User();
        user.setName("lisi");
        user.setAge(21);
        return R.success(user);
    }

    @GetMapping("/test2")
    @RepeatSubmit
    public R<String> testRepeatSubmit(String name){
        return R.success(name);
    }



    @Data
    public static class User{
        private String name;
        private Integer age;
    }
}
