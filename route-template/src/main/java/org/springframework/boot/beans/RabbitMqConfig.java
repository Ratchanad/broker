package org.springframework.boot.beans;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StringUtils;

@EnableAutoConfiguration(exclude = {RabbitAutoConfiguration.class})
@Configuration
public class RabbitMqConfig {
    @Value("${rabbitmq.inbound.uri:}")
    private String inboundUri;

    @Value("${rabbitmq.inbound.username:}")
    private String inboundUsername;

    @Value("${rabbitmq.inbound.password:}")
    private String inboundPassword;

    @Value("${rabbitmq.inbound.virtual-host:}")
    private String inboundVirtualHost;

    @Value("${rabbitmq.outbound.uri:}")
    private String outboundUri;

    @Value("${rabbitmq.outbound.username:}")
    private String outboundUsername;

    @Value("${rabbitmq.outbound.password:}")
    private String outboundPassword;

    @Value("${rabbitmq.outbound.virtual-host:}")
    private String outboundVirtualHost;

    @Bean
    @Primary
    @Profile({"rabbitmq-inbound"})
    public CachingConnectionFactory inboundConnectionFactory() {
        if (StringUtils.hasText(inboundUri)) {
            CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
            connectionFactory.setUri(inboundUri);
            connectionFactory.setUsername(inboundUsername);
            connectionFactory.setPassword(inboundPassword);
            if (!inboundVirtualHost.isEmpty())
                connectionFactory.setVirtualHost(inboundVirtualHost);
            return connectionFactory;
        }
        throw new IllegalArgumentException("Inbound URI is not specified");
    }

    @Bean
    @Profile({"rabbitmq-outbound"})
    public CachingConnectionFactory outboundConnectionFactory() {
        if (StringUtils.hasText(outboundUri)) {
            CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
            connectionFactory.setUri(outboundUri);
            connectionFactory.setUsername(outboundUsername);
            connectionFactory.setPassword(outboundPassword);
            if (!outboundVirtualHost.isEmpty())
                connectionFactory.setVirtualHost(outboundVirtualHost);
            return connectionFactory;
        }
        throw new IllegalArgumentException("Outbound URI is not specified");
    }
}
