package com.example.authencation.Project.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class OTPCacheRepository implements CacheRepository {

    private long ttl;
    private StringRedisTemplate redisTemplate;
    private ValueOperations<String, String> valueOps;

    @Autowired
    public OTPCacheRepository(StringRedisTemplate redisTemplate,
                              @Value("${spring.redis.timeToLive}") long ttl) {
        this.redisTemplate = redisTemplate;
        valueOps = redisTemplate.opsForValue();
        this.ttl = ttl;
    }

    @Override
    public void put(String key, Integer value) {
        try {
            valueOps.set(key, String.valueOf(value));
            redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
        } catch (RuntimeException e) {
            throw new OTPServiceException("Error while saving to cache ", e);
        }
    }

    @Override
    public Optional<String> get(String key) {
        try {
            Boolean b = redisTemplate.hasKey(key);
            if (Boolean.TRUE.equals(b)) {
                return Optional.ofNullable(valueOps.get(key));
            } else {
                return Optional.empty();
            }
        } catch (RuntimeException e) {
            throw new OTPServiceException("Error while retrieving from the cache ", e);
        }
    }

    @Override
    public void remove(String key) {
        try {
            redisTemplate.delete(key);
        } catch (RuntimeException e) {
            throw new OTPServiceException("Error while removing from the cache ", e);
        }
    }

}
