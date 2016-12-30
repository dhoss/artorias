package com.artorias.configuration;

import com.artorias.exception.ExceptionTranslator;
import com.artorias.service.DefaultPostService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.*;
import org.jooq.conf.Settings;
import org.jooq.impl.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;

/**
 * Created by devin on 11/13/16.
 */
@Configuration
public class AppConfig {

   /* @Bean
    public HikariDataSource hikariDataSource() {

        HikariConfig config = new HikariConfig();

        config.addDataSourceProperty("url", env.getProperty("spring.datasource.url"));
        config.addDataSourceProperty("username", env.getProperty("spring.datasource.username"));
        config.addDataSourceProperty("password", env.getProperty("spring.datasource.password"));

        return new HikariDataSource(config);
    }*/

    /*
    @Qualifier("jooqConfig")
    public org.jooq.Configuration jooqConfig() {
        initDataSource();

        // Configuration for JOOQ
        return new DefaultConfiguration()
                .set(SQLDialect.POSTGRES_9_5)
                .set(new DataSourceConnectionProvider(dataSource))
                .set(new Settings().withExecuteLogging(true));
    }*/

    @Autowired
    private Environment env;
/*
    @Bean
    public DSLContext dsl() {
        initDataSource();

        DefaultConfiguration config = new DefaultConfiguration();
        config.set(SQLDialect.POSTGRES_9_5)
              .set(new DataSourceConnectionProvider(dataSource))
              .set(new Settings().withExecuteLogging(true));

        return DSL.using(config);
    }
*/

    @Bean
    public DSLContext dsl(org.jooq.Configuration config) {
        return new DefaultDSLContext(config);
    }

    @Bean
    public ConnectionProvider connectionProvider(DataSource dataSource) {
        return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
    }

    @Bean
    public TransactionProvider transactionProvider() {
        return new SpringTransactionProvider();
    }

    @Bean
    public ExceptionTranslator exceptionTranslator() {
        return new ExceptionTranslator();
    }

    @Bean
    public ExecuteListenerProvider executeListenerProvider(ExceptionTranslator exceptionTranslator) {
        return new DefaultExecuteListenerProvider(exceptionTranslator);
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public org.jooq.Configuration jooqConfig(ConnectionProvider connectionProvider,
                                             TransactionProvider transactionProvider, ExecuteListenerProvider executeListenerProvider) {

        return new DefaultConfiguration()
                .derive(connectionProvider)
                .derive(transactionProvider)
                .derive(executeListenerProvider)
                .derive(SQLDialect.POSTGRES_9_5);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public DefaultPostService defaultPostService(DSLContext dsl, ModelMapper mapper) {
        return new DefaultPostService(dsl, mapper);
    }

}
