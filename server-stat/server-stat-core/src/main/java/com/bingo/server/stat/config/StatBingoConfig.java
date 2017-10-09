package com.bingo.server.stat.config;

import com.bingo.framework.config.ApplicationConfig;
import com.bingo.framework.config.RegistryConfig;
import com.bingo.server.utils.BingoConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ZhangGe on 2017/7/12.
 */
@Configuration
public class StatBingoConfig {

    private static final Logger logger = LoggerFactory.getLogger(StatBingoConfig.class);

    @Value("${application.name}")
    private String applicationName;

    @Value("${zookeeper.address}")
    private String zookeeper;

    @Value("${process.port}")
    private int port;

    @Bean
    public ApplicationConfig getApplicationConfig() {
        return BingoConfig.getApplication(applicationName);
    }

    @Bean
    public RegistryConfig getRegistryConfig() {
        return BingoConfig.getRegistry(zookeeper);
    }

}
