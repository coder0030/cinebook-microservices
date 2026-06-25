package com.movie.movie_service.Redis;

import com.movie.movie_service.Entity.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MovieRedisService {

    private RedisTemplate<String, Object> redisTemplate;

    public void setMovie(String key, Movie movie) {
        redisTemplate.opsForValue().set(key, movie);
    }

    public Movie getMovie(String key) {
        return (Movie) redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void setIfPresent(String key, Movie movie) {
        redisTemplate.opsForValue().setIfPresent(key, movie);
    }

    public void setIfAbsent(String key, Movie movie) {
        redisTemplate.opsForValue().setIfAbsent(key, movie);
    }

    public void setMovieWithExpireTime(String key, Movie movie, long expireTime,  TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, movie, expireTime, timeUnit);
    }
}
