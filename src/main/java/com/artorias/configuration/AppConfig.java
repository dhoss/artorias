package com.artorias.configuration;

import com.artorias.service.DefaultPostService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by devin on 11/13/16.
 */
@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public DefaultPostService defaultPostService() {
        return new DefaultPostService();
    }

}
