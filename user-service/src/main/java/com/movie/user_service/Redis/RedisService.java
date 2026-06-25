package com.movie.user_service.Redis;

import com.movie.user_service.Entity.User;
import io.lettuce.core.dynamic.domain.Timeout;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private RedisTemplate<String, Object> redisTemplate;

    public User getUserByEmail(String email) {
        return (User) redisTemplate.opsForValue().get(email);
    }

    public User getUserByName(String username) {
        return (User) redisTemplate.opsForValue().get(username);
    }

    public User  getUserById(String id) {
        return (User) redisTemplate.opsForValue().get(id);
    }

    public void setUser(String key, User user) {
        redisTemplate.opsForValue().set(key, user);
    }

    public void setWithExpireTime(String key, User user, long timeout, TimeUnit timeUnit) {
       redisTemplate.opsForValue().set(key, user, timeout, timeUnit);
    }

    public Boolean setIfPresent(String key, User user) {
        return redisTemplate.opsForValue().setIfPresent(key, user);
    }

    public Boolean setIfAbsent(String key, User user) {
       return redisTemplate.opsForValue().setIfAbsent(key, user);
    }

    public Long increment(String key, User user) {
        return redisTemplate.opsForValue().increment(key, user.getId());
    }


    public Boolean deleteUser(String key) {
        return redisTemplate.delete(key);
    }

    public void setUserWithExpiry(String idKey, User user, int cacheTtlMinutes, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(idKey, user, cacheTtlMinutes, timeUnit);
    }

    public void delete(String nameKey) {
        redisTemplate.delete(nameKey);
    }

    public Long removeFromSet(String key, Object... values) {
        if (values == null || values.length == 0) {
            return 0L;
        }
        return redisTemplate.opsForSet().remove(key, values);
    }

    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public void invalidateByPattern(String s) {
        redisTemplate.delete(s);
    }
}
