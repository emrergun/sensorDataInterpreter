package com.thy.sensorDataInterpreter.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.thy.sensorDataInterpreter.repository.jpa")
@EnableMongoRepositories(basePackages = "com.thy.sensorDataInterpreter.repository.mongo")
public class RepositoryConfiguration {
}
