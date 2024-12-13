package org.springframework.boot.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqToRabbitMqTemplate extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        routeTemplate("rabbitMqToRabbitMqTemplate")
                .templateParameter("appId")
                .templateParameter("directAfterConsume", "N")
                .templateParameter("directBeforeSend", "N")
                .templateParameter("transform", "N")
                .from(
                        "spring-rabbitmq:{{{{appId}}.inbound.exchange.name}}?connectionFactory=#inboundConnectionFactory&routingKey={{{{appId}}.inbound.routing.key}}"
                                + "&queues={{{{appId}}.inbound.queue.name}}&arg.queue.durable=true&exchangeType=topic&autoDeclare=true")
                .log("Message Id ({{appId}}) : inbound message exchange name: {{{{appId}}.inbound.exchange.name}} routingKey: {{{{appId}}.inbound.routing.key}} queue: {{{{appId}}.inbound.queue.name}}")
                .choice()
                .when(simple("'{{directAfterConsume}}' != 'N'"))
                .to("direct:{{directAfterConsume}}")
                .log(" direct sent : {{directAfterConsume}}")
                .end()
                .choice()
                .when(simple("'{{transform}}' != 'N'"))
                .toD("xslt:xslt/{{transform}}.xsl")
                .log(" transformer sent : {{transform}}")
                .end()
                .choice()
                .when(simple("'{{directBeforeSend}}' != 'N'"))
                .log("Direct before send: {{directBeforeSend}}")
                .to("direct:{{directBeforeSend}}")
                .end()
                .to(
                        "spring-rabbitmq:{{{{appId}}.outbound.exchange.name}}?connectionFactory=#outboundConnectionFactory&routingKey={{{{appId}}.outbound.routing.key}}"
                                + "&exchangeType=topic")
                .log("Sent to outbound exchange with exchange name {{{{appId}}.outbound.exchange.name}} routingKey: {{{{appId}}.outbound.routing.key}}")
                .onException(Exception.class)
                .log("Error processing message: ${exception.message}")
                .to("spring-rabbitmq:{{{{appId}}.dlq.exchange.name}}?connectionFactory=#outboundConnectionFactory&routingKey={{{{appId}}.dlq.routing.key}}"
                        + "&arg.queue.durable=true&exchangeType=topic")
                .end();
    }
}
