package com.sheikh.productmanager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class RedisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> T get(String key, Class<T> entityClass) {
        try {
            Object value = redisTemplate.opsForValue().get(key);
            if (value != null) {
                return objectMapper.readValue(value.toString(), entityClass);
            }
        } catch (Exception e) {
            log.error("Error fetching from Redis", e);
        }
        return null;
    }

    public <T> void save(String key, T value) {
        try {
            redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(value));
        } catch (Exception e) {
            log.error("Error saving to Redis", e);
        }
    }

    public void delete(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("Error deleting from Redis", e);
        }
    }
}