package org.springframework.boot.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.support.builder.Namespaces;
import org.springframework.stereotype.Component;

@Component
public class Test01Route extends RouteBuilder {
    @Override
    public void configure() throws Exception{
        String appId = "exam.rabbit";
        setHeader(appId);
        templatedRoute("rabbitMqToRabbitMqTemplate")
                .routeId(appId + "_rabbitmq_to_rabbitmq")
                .parameter("appId", appId)
                .parameter("directAfterConsume", String.format("%s_setHeader", appId));
    }

    private void setHeader(String appId) {
        var nameSpace = new Namespaces("wts", "http://WTSSendLibrary");
        from(String.format("direct:%s_setHeader", appId))
                .setHeader("_branch_code_",xpath("@id",String.class,nameSpace))
                .setHeader("_as_of_date_", simple("${date:now:yyyyMMdd}"))
                .setHeader("_round_no_", constant('1'))
                .end();
    }
}
