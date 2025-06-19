package com.sodagift.biz.config;

import com.sodagift.common.datasource.RoutingDataSource;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
@EntityScan({
        "com.sodagift.biz.domain.balance.**",
        "com.sodagift.biz.domain.delivery.**",
        "com.sodagift.biz.domain.exchange.**",
        "com.sodagift.biz.domain.image.**",
        "com.sodagift.biz.domain.order.**",
        "com.sodagift.biz.domain.payment.**",
        "com.sodagift.biz.domain.product.**",
        "com.sodagift.biz.domain.account.**",
        "com.sodagift.biz.domain.discount.**",
        "com.sodagift.biz.domain.logging.**",
})
@EnableJpaRepositories({
        "com.sodagift.biz.domain.balance.**",
        "com.sodagift.biz.domain.delivery.**",
        "com.sodagift.biz.domain.exchange.**",
        "com.sodagift.biz.domain.image.**",
        "com.sodagift.biz.domain.order.**",
        "com.sodagift.biz.domain.payment.**",
        "com.sodagift.biz.domain.product.**",
        "com.sodagift.biz.domain.account.**",
        "com.sodagift.biz.domain.discount.**",
        "com.sodagift.biz.domain.logging.**",
})
public class DatabaseConfiguration {

    @Bean
    public AuditorAware<String> auditAware() {
        return () -> {
            Optional<Authentication> authentication = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
            if (authentication.isEmpty()) {
                return Optional.of("system");
            }
            return authentication.map(Authentication::getName);
        };
    }

    @Bean
    @ConfigurationProperties(prefix = "datasource.gift.writer")
    public DataSource giftWriterDataSource() {
        return new HikariDataSource();
    }

    @Bean
    @ConfigurationProperties(prefix = "datasource.gift.reader")
    public DataSource giftReaderDataSource() {
        return new HikariDataSource();
    }

    @Bean
    public DataSource giftDataSource(
            @Qualifier("giftWriterDataSource") DataSource writer,
            @Qualifier("giftReaderDataSource") DataSource reader) {
        final RoutingDataSource routingDataSource = new RoutingDataSource();

        Map<Object, Object> routingMap = Map.of(
                RoutingDataSource.WRITER, writer,
                RoutingDataSource.READER, reader
        );

        routingDataSource.setTargetDataSources(routingMap);
        routingDataSource.setDefaultTargetDataSource(writer);

        return routingDataSource;
    }

    @Bean
    @Primary
    public DataSource giftLazyConnectionDataSourceProxy(@Qualifier("giftDataSource") DataSource dataSource) {
        return new LazyConnectionDataSourceProxy(dataSource);
    }
}
