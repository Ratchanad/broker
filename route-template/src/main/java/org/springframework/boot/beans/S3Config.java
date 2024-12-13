package org.springframework.boot.beans;

import org.apache.camel.tooling.model.Strings;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
public class S3Config {
    @Value("${s3.profile.name:}")
    private String s3ProfileName;
    @Value("${s3.endpoint:}")
    private String s3Endpoint;

    @Bean("amazonS3Client")
    @Profile({"s3-inbound", "s3-outbound"})
    public S3Client s3Client() {
        if (Strings.isNullOrEmpty(s3Endpoint) || Strings.isNullOrEmpty(s3ProfileName)) {
            throw new IllegalArgumentException("S3Client is not specified");
        }
        return S3Client.builder()
                .endpointOverride(URI.create(s3Endpoint))
                .region(Region.of("ap-southeast-1")) // Specify your AWS region
                .credentialsProvider(ProfileCredentialsProvider.create(s3ProfileName))
                .build();
    }
}
