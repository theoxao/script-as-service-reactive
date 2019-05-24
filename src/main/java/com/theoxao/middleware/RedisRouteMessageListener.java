package com.theoxao.middleware;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theoxao.entity.BaseRouteData;
import com.theoxao.route.RouteHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;

import java.io.IOException;

/**
 * @author theo
 * @date 2019/5/24
 */
@Configuration
public class RedisRouteMessageListener {


    private final StringRedisTemplate redisTemplate;

    public RedisRouteMessageListener(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Bean
    public RedisMessageListenerContainer container(RouteHandler routeHandler) {
        ObjectMapper objectMapper = new ObjectMapper();
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisTemplate.getConnectionFactory());
        container.setTaskExecutor(new ConcurrentTaskExecutor());
        PatternTopic topic = new PatternTopic("*");
        container.addMessageListener(new MessageListener() {
            @Override
            public void onMessage(@NotNull Message message, byte[] bytes) {
                String body = new String(message.getBody());
                String channel = new String(message.getChannel());
                System.out.println(channel);
                if (channel.contains("set")) {
                    String raw = redisTemplate.boundValueOps(body).get();
                    try {
                        BaseRouteData routeData = objectMapper.readValue(raw, BaseRouteData.class);
                        routeHandler.addRoute(routeData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (channel.contains("del")) {
                    routeHandler.removeRoute(body);
                }
            }
        }, topic);
        return container;
    }

}
