package com.listener.clientservicelistener.config;

import jakarta.jms.Queue;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActiveMqConfig {
    // Cola principal donde escucha ClientServiceListener
    @Bean
    public Queue clientQueue() {
        return new ActiveMQQueue("client.queue");
    }

    @Bean
    public Queue replyQueue() {
        return new ActiveMQQueue("client.reply.queue");
    }
}

