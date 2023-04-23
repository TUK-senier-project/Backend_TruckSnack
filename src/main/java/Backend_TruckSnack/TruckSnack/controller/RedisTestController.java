package Backend_TruckSnack.TruckSnack.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class RedisTestController {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @GetMapping("/redisTest")
    public String redisTest()  {
        String key = "Testkey";
        String value = "Testvalue";
        redisTemplate.opsForValue().set(key, value);
        String retrievedValue = redisTemplate.opsForValue().get(key);
        System.out.println("Retrieved value: " + retrievedValue);

        return retrievedValue;
    }
}
