package com.dev.coder.multiDbConn.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

    @Primary
    @Bean(name = "courseDataSource")
    @ConfigurationProperties(prefix = "spring.courses.datasource")
    public DataSource courseDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "studentDataSource")
    @ConfigurationProperties(prefix = "spring.students.datasource")
    public DataSource studentDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "courseJdbcTemplate")
    public JdbcTemplate courseJdbcTemplate(@Qualifier("courseDataSource") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "studentJdbcTemplate")
    public JdbcTemplate studentJdbcTemplate(@Qualifier("studentDataSource") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
