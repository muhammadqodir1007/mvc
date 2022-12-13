package com.epam.dao.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:dbConnection.properties")
public class PostgreSqlConfig {

    /**
     * Class {@code PostgreSqlDataBaseConfig} contains spring configuration for dao subproject.
     */


    @Bean
    public DataSource dataSource(@Value("${db.user}") String user,
                                 @Value("${db.password}") String password,
                                 @Value("${db.driver}") String className,
                                 @Value("${db.url}") String connectionUrl) {
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


