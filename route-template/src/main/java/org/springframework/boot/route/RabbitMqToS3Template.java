package org.springframework.boot.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqToS3Template extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        routeTemplate("rabbitMqToS3Template")
                .templateParameter("appId")
                .templateParameter("directAfterConsume")

                .from("spring-rabbitmq:{{{{appId}}.inbound.exchange.name}}?connectionFactory=#inboundConnectionFactory&routingKey={{{{appId}}.inbound.routing.key}}&queues={{{{appId}}.inbound.queue.name}}&arg.queue.durable=true&exchangeType=topic")
                .to("log:camelLogger?level=INFO&showAll=true&showBody=false")
                .to("direct:{{directAfterConsume}}")
                .to("xslt:xslt/{{appId}}.xsl")
                .process(exchange-> {
                    String body = exchange.getIn().getBody(String.class);
                    int lineCount = body.split("\n").length;
                    body += "TOTAL|" + lineCount + "\n";
                    exchange.getIn().setBody(body);

                    // Get current date from the header
                    String timestamp = exchange.getIn().getHeader("_as_of_date_", String.class);

                    // Get warehouseCode from the header
                    String warehouseCode = exchange.getIn().getHeader("_warehouse_code_", String.class);

                    // Get nameFile from the header
                    String nameFile = exchange.getIn().getHeader("_name_file_", String.class);

                    // Create the fileName
                    String fileName = nameFile + timestamp + "." + warehouseCode + ".txt";

                    // Prepend the S3 path
                    String s3Key = "gr_out_sap/archive/" + fileName;

                    // Set the S3 key in the headers
                    exchange.getIn().setHeader("CamelAwsS3Key", s3Key);
                })
                .to("aws2-s3://{{{{appId}}.output.aws-s3.bucket.name}}?amazonS3Client=#amazonS3Client&region={{{{appId}}.output.aws-s3.region}}")
                .log(LoggingLevel.INFO, "Message {{appId}}: <<${header.CamelAwsS3Key}>> consumed from S3 bucket")
                .end();
    }
}
