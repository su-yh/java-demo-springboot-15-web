package com.eb.mq.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.NumberSerializer;
import com.eb.mq.product.component.MqProductComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJackson2MessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.MimeTypeUtils;

/**
 * @author suyh
 * @since 2024-09-04
 */
@Configuration
@Slf4j
public class RabbitmqConfig {

    @Bean
    public MessageConverter jacksonMessageConverter() {
        ObjectMapper mapper = new ObjectMapper();

        // 序列化的时候对null 属性进行忽略，所有的null 属性都不会被序列化到json 中。
        // ignored non null field
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 反序列化时,遇到未知属性(那些没有对应的属性来映射的属性,并且没有任何setter或handler来处理这样的属性)时
        // 是否引起结果失败(通过抛JsonMappingException异常).
        // 此项设置只对那些已经尝试过所有的处理方法之后并且属性还是未处理
        // (这里未处理的意思是:最终还是没有一个对应的类属性与此属性进行映射)的未知属性才有影响.
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, NumberSerializer.instance)
                .addSerializer(Long.TYPE, NumberSerializer.instance);
        mapper.registerModules(simpleModule);

        // 要求， content_type 的值为 text/plain 而不是 application/json
        // return new Jackson2JsonMessageConverter(mapper);
        return new JacksonMessageConverter(mapper);
    }

    public static class JacksonMessageConverter extends AbstractJackson2MessageConverter {

        protected JacksonMessageConverter(ObjectMapper objectMapper) {
            super(objectMapper, MimeTypeUtils.parseMimeType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN), "*");
        }
    }

    @Bean(name = "primaryConnectionFactory")
    @Primary
    public ConnectionFactory primaryConnectionFactory(
            @Value("${spring.rabbitmq.primary.host}") String host,
            @Value("${spring.rabbitmq.primary.port}") int port,
            @Value("${spring.rabbitmq.primary.username}") String username,
            @Value("${spring.rabbitmq.primary.password}") String password,
            @Value("${spring.rabbitmq.primary.virtual-host}") String virtualHost) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        return connectionFactory;
    }

    @Bean(name = "flinkConnectionFactory")
    public ConnectionFactory flinkConnectionFactory(
            @Value("${spring.rabbitmq.svip.host}") String host,
            @Value("${spring.rabbitmq.svip.port}") int port,
            @Value("${spring.rabbitmq.svip.username}") String username,
            @Value("${spring.rabbitmq.svip.password}") String password,
            @Value("${spring.rabbitmq.svip.virtual-host}") String virtualHost) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        return connectionFactory;
    }

    @Bean(name = "flinkRabbitTemplate")
    public RabbitTemplate flinkRabbitTemplate(
            MessageConverter messageConverter,
            @Qualifier("flinkConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean("flinkContainerFactory")
    public SimpleRabbitListenerContainerFactory flinkContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            @Qualifier("flinkConnectionFactory") ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConcurrentConsumers(10);
        factory.setMaxConcurrentConsumers(20);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean
    public MqProductComponent mqProductComponent(
            @Qualifier("flinkRabbitTemplate") RabbitTemplate rabbitTemplate) {
        return new MqProductComponent(rabbitTemplate);
    }
}
