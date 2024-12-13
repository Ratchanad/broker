package org.springframework.boot.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.language.xpath.XPathBuilder;
import org.springframework.stereotype.Component;

@Component
public class Test02Route extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        String appId = "exam.s3";
        setHeader(appId);
        templatedRoute("rabbitMqToS3Template")
                .routeId(appId+"_rabbitmq_to_S3")
                .parameter("appId", appId)
                .parameter("directAfterConsume",String.format("%s_setHeader",appId));

    }
    private void setHeader(String appId){
        from(String.format("direct:%s_setHeader",appId))
                .setHeader("_warehouse_code_",
                        XPathBuilder.xpath("wts:grsap/data_content/header/warehouse_code", String.class).namespace("wts",
                                "http://SENDGRDLLibrary"))
                .setHeader("_progId_",constant("MML06_SAP"))
                .setHeader("_as_of_date_",simple("${date:now:yyyyMMddHHmmss}"))
                .setHeader("_name_file_",constant("test03"))
                .end();
    }
}
