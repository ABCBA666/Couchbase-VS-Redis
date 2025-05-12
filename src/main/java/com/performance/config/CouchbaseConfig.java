package com.performance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;
import org.springframework.data.couchbase.core.convert.CouchbaseCustomConversions;
import java.util.Collections;

/**
 * Couchbase配置类
 * 配置Couchbase连接和基本设置
 */
@Configuration
@EnableCouchbaseRepositories(basePackages = {"com.performance.repository"})
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

    @Override
    public String getConnectionString() {
        return "couchbase://localhost";
    }

    @Override
    public String getUserName() {
        return "Administrator";
    }

    @Override
    public String getPassword() {
        return "123456";
    }

    @Override
    public String getBucketName() {
        return "test";
    }

    @Bean
    @Primary
    public CouchbaseCustomConversions myCouchbaseCustomConversions() {
        return new CouchbaseCustomConversions(Collections.emptyList());
    }
} 