package com.artorias.configuration;

import com.artorias.database.jooq.tables.pojos.Author;
import com.artorias.database.jooq.tables.pojos.Post;
import com.artorias.dto.PostDTO;
import com.artorias.exception.ExceptionTranslator;
import com.artorias.service.DefaultPostService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.*;
import org.jooq.conf.Settings;
import org.jooq.impl.*;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NameTokenizers;
import org.modelmapper.jooq.RecordValueReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;
import java.util.Map;

/**
 * Created by devin on 11/13/16.
 */
@Configuration
public class AppConfig {

    @Autowired
    private Environment env;

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
        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration().addValueReader(new RecordValueReader());
        mapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE);

        return mapper;
    }

    @Bean
    public DefaultPostService defaultPostService(DSLContext dsl, ModelMapper mapper) {
        return new DefaultPostService(dsl, mapper);
    }

}
