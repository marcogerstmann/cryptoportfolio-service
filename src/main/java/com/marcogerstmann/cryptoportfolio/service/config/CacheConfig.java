package com.marcogerstmann.cryptoportfolio.service.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty("app.cache.enabled")
@Configuration
@EnableCaching
public class CacheConfig {

}
