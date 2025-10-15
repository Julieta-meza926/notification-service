package com.listener.clientservicelistener.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.shared.dto.ClientEventDTO;
import jakarta.jms.ConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import java.util.HashMap;
import java.util.Map;

@EnableJms
@Configuration
public class JmsConfig {

    @Bean
    public MessageConverter jacksonJmsMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);           // Usamos texto para mensajes
        converter.setTypeIdPropertyName("_type");           // Nombre de la propiedad para el tipo

        // Mapeamos el typeId que enviamos en los mensajes con la clase real
        Map<String, Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put("ClientEventDTO", com.mycompany.shared.dto.ClientEventDTO.class);
        converter.setTypeIdMappings(typeIdMappings);

        // Inyectamos ObjectMapper de Spring (para compatibilidad con JavaTime, etc)
        converter.setObjectMapper(objectMapper);

        return converter;
    }


    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(
            ConnectionFactory connectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer,
            MessageConverter messageConverter) {

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setMessageConverter(messageConverter);
        factory.setConcurrency("1-1");

        configurer.configure(factory, connectionFactory);

        return factory;
    }
    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        JmsTemplate template = new JmsTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        template.setReceiveTimeout(5000);
        return template;
    }
}