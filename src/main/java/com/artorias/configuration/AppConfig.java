package com.artorias.configuration;

import com.artorias.service.DefaultPostService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created by devin on 11/13/16.
 */
@Configuration
public class AppConfig {

    private HikariDataSource dataSource;

    private void initDataSource() {

        HikariConfig config = new HikariConfig();

        config.addDataSourceProperty("url", env.getProperty("spring.datasource.url"));
        config.addDataSourceProperty("username", env.getProperty("spring.datasource.username"));
        config.addDataSourceProperty("password", env.getProperty("spring.datasource.password"));

        dataSource = new HikariDataSource(config);
    }

    @Qualifier("jooqConfig")
    public org.jooq.Configuration jooqConfig() {
        initDataSource();

        // Configuration for JOOQ
        return new DefaultConfiguration()
                .set(SQLDialect.POSTGRES_9_5)
                .set(new DataSourceConnectionProvider(dataSource))
                .set(new Settings().withExecuteLogging(true));
    }

    @Autowired
    private Environment env;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public DefaultPostService defaultPostService(DSLContext dsl, ModelMapper mapper) {
        return new DefaultPostService(dsl, mapper);
    }

}
