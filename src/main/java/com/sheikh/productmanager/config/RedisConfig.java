package com.sheikh.productmanager.config;

import com.sheikh.productmanager.model.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

    @Configuration
    public class RedisConfig {

        @Bean
        public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
            RedisTemplate<String, Object> template = new RedisTemplate<>();
            template.setConnectionFactory(redisConnectionFactory);

            // Use Jackson2JsonRedisSerializer for value serialization
            Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);

            // Set serializers
            template.setKeySerializer(new StringRedisSerializer());
            template.setValueSerializer(serializer);
            template.setHashKeySerializer(new StringRedisSerializer());
            template.setHashValueSerializer(serializer);

            return template;
        }
    }