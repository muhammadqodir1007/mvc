package com.epam.dao.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application-test.properties")
@Profile("test")
@ComponentScan("com.epam")

/**
 * Class {@code PostgreSqlDataBaseConfig} contains spring configuration for dao subproject tests.
 *
 *
 */ public class PostgreSqlConfigForTest {
    /**
     * Create bean {@link DataSource} which will be used as data source.
     *
     * @return the basicDataSource
     */
    @Bean
    public DataSource dataSource(@Value("${db.user}") String user, @Value("${db.password}") String password, @Value("${db.driver}") String className, @Value("${db.testUrl}") String connectionUrl) {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(password);
        basicDataSource.setDriverClassName(className);
        basicDataSource.setUrl(connectionUrl);
        return basicDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
