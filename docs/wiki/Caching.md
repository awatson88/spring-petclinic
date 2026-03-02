# Caching

## Overview

The application uses **JCache (JSR-107)** with **Caffeine** as the caching provider to cache veterinarian data.

## Configuration

Caching is configured in `CacheConfiguration.java`:

```java
@Configuration(proxyBeanMethods = false)
@EnableCaching
class CacheConfiguration {

    @Bean
    public JCacheManagerCustomizer petclinicCacheConfigurationCustomizer() {
        return cm -> cm.createCache("vets", cacheConfiguration());
    }

    private Configuration<Object, Object> cacheConfiguration() {
        return new MutableConfiguration<>().setStatisticsEnabled(true);
    }
}
```

## Cache Details

| Cache Name | Purpose | Statistics |
|-----------|---------|-----------|
| `vets` | Caches veterinarian data | Enabled (accessible via JMX and Actuator) |

## Monitoring

Cache statistics are available through:
- **Spring Boot Actuator**: `GET /actuator/caches`
- **JMX**: via JCache statistics MBeans

## Dependencies

| Dependency | Purpose |
|-----------|---------|
| `spring-boot-starter-cache` | Spring caching abstraction |
| `javax.cache:cache-api` | JCache API (JSR-107) |
| `com.github.ben-manes.caffeine:caffeine` | High-performance caching library |
