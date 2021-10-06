package com.example.sqsreadandwritedybamodb;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;

@SpringBootApplication(exclude = {ContextStackAutoConfiguration.class})
public class SqsReadAndWriteDybamodbApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqsReadAndWriteDybamodbApplication.class, args);
    }

}
